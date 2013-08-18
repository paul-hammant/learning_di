package learning.di;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.XmlObjectParser;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
@Category(IntegrationTestGroup.class)
public class IntegrationTest {

    @Test
    public void unMappedMethodShouldBeHandledSilently() throws IOException {

        // First request - reusing same JSESSIONID
        HttpResponse execution = makeRequest("/a/b.txt?c=d")
                .setThrowExceptionOnExecuteError(false)
                .execute();

        assertThat(execution.getStatusCode(), equalTo(404));
    }

    @Test
    public void applicationsSessionsAndRequestScopesShouldWorkAsExpected() throws IOException {

        String INVARIANT_INVENTORY = "Inventory# 1, contents: [TV, iMac, Washer, InkJet Printer]";


        // First request - making a session
        HttpResponse response = makeRequest("/Cart/addTo.txt?item=TV").execute();
        assertThat(response.parseAs(String.class), equalTo("OK"));
        String jSessionId = getJSessionID(response);

        // Second request - reusing SAME session (via cookie)
        HttpRequest request = makeRequest("/Cart/addTo.txt?item=iMac");
        request.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        response = request.execute();
        assertThat(response.parseAs(String.class), equalTo("OK"));

        request = makeRequest("/Cart/addTo.txt?item=Socks");
        request.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        response = request.execute();
        assertThat(response.parseAs(String.class), equalTo("Not In Inventory"));

        request = makeRequest("/Checkout/calculateSalesTax.txt?zipCode=12345");
        request.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        response = request.execute();
        assertThat(response.parseAs(String.class), equalTo("SalesTax{cart: Cart# 1, Inventory: (" + INVARIANT_INVENTORY + "), contents: [TV, iMac], order: Order# 1, zipCode:12345, taxAmountCents:875}"));

        request = makeRequest("/Checkout/calculateSalesTax.txt?zipCode=67890");
        request.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        response = request.execute();
        assertThat(response.parseAs(String.class), equalTo("SalesTax{cart: Cart# 1, Inventory: (" + INVARIANT_INVENTORY + "), contents: [TV, iMac], order: Order# 1, zipCode:67890, taxAmountCents:875}"));

        request = makeRequest("/Checkout/cartContents.txt");
        request.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        response = request.execute();
        assertThat(response.parseAs(String.class), equalTo("CartContents{cart=Cart# 1, Inventory: (" + INVARIANT_INVENTORY + "), contents: [TV, iMac], order=Order# 1, upSell=UpSell# 1, item: InkJet Printer, Cart: (Cart# 1, Inventory: (" + INVARIANT_INVENTORY + "), contents: [TV, iMac])}"));

        // new session
        response = makeRequest("/Checkout/calculateSalesTax.txt?zipCode=24680").execute();
        assertThat(response.parseAs(String.class), equalTo("SalesTax{cart: Cart# 2, Inventory: (" + INVARIANT_INVENTORY + "), contents: [], order: Order# 2, zipCode:24680, taxAmountCents:875}"));

        // another new session
        response = makeRequest("/Checkout/cartContents.txt").execute();
        assertThat(response.parseAs(String.class), equalTo("CartContents{cart=Cart# 3, Inventory: (" + INVARIANT_INVENTORY + "), contents: [], order=Order# 3, upSell=UpSell# 2, item: iMac, Cart: (Cart# 3, Inventory: (" + INVARIANT_INVENTORY + "), contents: [])}"));

    }

    private String getJSessionID(HttpResponse execution) {
        HttpHeaders headers = execution.getHeaders();
        List list = (List) headers.get("set-cookie");
        String jSessionId = (String) list.get(0);
        jSessionId = jSessionId.split(";")[0];
        return jSessionId;
    }

    private HttpRequest makeRequest(final String url) throws IOException {
        return new NetHttpTransport()
                .createRequestFactory(new StringResponse())
                .buildGetRequest(new GenericUrl("http://localhost:8080" + url));
    }


    private static class StringResponse implements HttpRequestInitializer {
        public void initialize(HttpRequest request) {
            request.setParser(new XmlObjectParser(new XmlNamespaceDictionary()) {
                public <T> T parseAndClose(InputStream inputStream, Charset charset, Class<T> tClass) throws IOException {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(inputStream, writer, charset.name());
                    return (T) writer.toString();
                }
            });
        }
    }


}
