package learning.di.testing;

import learning.di.ApplicationInjection;
import learning.di.testing.components.Cart;
import learning.di.testing.components.Checkout;
import learning.di.testing.components.Inventory;
import learning.di.testing.components.Order;
import learning.di.testing.components.UpSell;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SampleFilter implements Filter {

    private Inventory inventory;

    public void init(FilterConfig filterConfig) throws ServletException {
        inventory = new Inventory();
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hResp = (HttpServletResponse) resp;

        HttpSession session = hReq.getSession(true);

        List<ApplicationInjection> sessionCompsInjectedInto = new ArrayList<ApplicationInjection>();

        String uri = hReq.getRequestURI();
        Object rVal = null;
        if (uri.startsWith("/Checkout")) {
            uri = uri.substring("/Checkout".length());
            Cart cart = getOrMakeCart(session, sessionCompsInjectedInto);
            Order order = getOrMakeOrder(session, sessionCompsInjectedInto);
            Checkout checkout = new Checkout(cart, order);
            if (uri.startsWith("/calculateSalesTax")) {
                uri = uri.substring("/calculateSalesTax".length());
                rVal = checkout.calculateSalesTax(hReq.getParameter("zipCode"));
            } else if (uri.startsWith("/cartContents")) {
                uri = uri.substring("/cartContents".length());
                rVal = checkout.cartContents(new UpSell(cart));
            } else {
                hResp.setStatus(404);
                return;
            }
        } else if (uri.startsWith("/Cart")) {
            uri = uri.substring("/Cart".length());
            Cart cart = getOrMakeCart(session, sessionCompsInjectedInto);
            if (uri.startsWith("/addTo")) {
                uri = uri.substring("/addTo".length());
                rVal = cart.addTo(hReq.getParameter("item"));
            } else {
                hResp.setStatus(404);
                return;
            }
        }

        if (rVal == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        if (uri.startsWith(".txt")) {
            resp.setContentType("text/plain");
            resp.getWriter().write(rVal.toString());
            return;
        }

        if (uri.startsWith(".html")) {
            // todo
        }
        if (uri.startsWith(".json")) {
            // todo
        }
        if (uri.startsWith(".xml")) {
            // todo
        }
            // Servlet Container could well serialize the session scoped objects:
        // best not to do that where application scoped objects are member variables.
        for (ApplicationInjection applicationInjection : sessionCompsInjectedInto) {
            applicationInjection.unInject();
        }
    }

    private synchronized Cart getOrMakeCart(HttpSession session, List<ApplicationInjection> sessionCompsInjectedInto) {
        String sessionKey = Cart.class.getName();
        Holder<Cart> h = (Holder<Cart>) session.getAttribute(sessionKey);
        Cart cart;
        if (h == null) {
            cart = new Cart();
            h = new Holder<Cart>(cart);
            session.setAttribute(sessionKey, h);
        } else {
            cart = h.held;
        }
        cart.setInventory(inventory);
        sessionCompsInjectedInto.add(new ApplicationInjection<Cart>(cart) {
            public void unInject() {
                instance.setInventory(null);
            }
        });
        return cart;
    }

    private synchronized Order getOrMakeOrder(HttpSession session, List<ApplicationInjection> sessionCompsInjectedInto) {
        String sessionKey = Order.class.getName();
        Holder<Order> h = (Holder<Order>) session.getAttribute(sessionKey);
        Order order;
        if (h == null) {
            order = new Order();
            h = new Holder<Order>(order);
            session.setAttribute(sessionKey, h);
        } else {
            order = h.held;
        }
        return order;
    }

    public void destroy() {
    }

    // So that other servlets/filters can't access
    // session.attributes directly for this (an IoC thing).
    private static class Holder<T> {
        private Holder(T held) {
            this.held = held;
        }

        private T held;
    }
}
