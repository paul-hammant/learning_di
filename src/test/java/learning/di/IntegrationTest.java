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

        // First request - making a session
        HttpResponse execution = makeRequest("/C/aWebMappedMethod.txt?zipCode=12345").execute();

        assertThat(execution.parseAs(String.class), equalTo("Foo{c='A: 1, B: 1\nC: 1', zipCode=12345}"));

        String jSessionId = getJSessionID(execution);

        // Second request - reusing SAME session (via cookie)
        HttpRequest httpRequest = makeRequest("/C/aWebMappedMethod.txt?zipCode=67890");
        httpRequest.setHeaders(new HttpHeaders().set("Cookie", asList(jSessionId)));
        execution = httpRequest.execute();

        assertThat(execution.parseAs(String.class), equalTo("Foo{c='A: 1, B: 1\nC: 2', zipCode=67890}"));

        // Third request - new session
        execution = makeRequest("/C/aWebMappedMethod.txt?zipCode=2468").execute();
        assertThat(execution.parseAs(String.class), equalTo("Foo{c='A: 1, B: 2\nC: 3', zipCode=2468}"));

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
