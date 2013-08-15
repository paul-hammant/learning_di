package learning.di;

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

public class SampleFilter implements Filter {

    private A a;

    public void init(FilterConfig filterConfig) throws ServletException {
        a = new A();
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hResp = (HttpServletResponse) resp;

        HttpSession session = hReq.getSession(true);

        String uri = hReq.getRequestURI();
        Object rVal = null;
        if (uri.startsWith("/C/aWebMappedMethod")) {
            C c = new C(getOrMakeLearningDiB(session));
            uri = uri.substring("/C/aWebMappedMethod".length());
            rVal = c.aWebMappedMethod(hReq.getParameter("zipCode"));
        }

        if (rVal == null) {
            hResp.setStatus(404);
            return;
        }

        if (uri.startsWith(".txt")) {
            resp.setContentType("text/plain");
            resp.getWriter().write(rVal.toString());
            return;
        }
    }

    private synchronized B getOrMakeLearningDiB(HttpSession session) {
        Holder h = (Holder) session.getAttribute(B.class.getName());
        if (h == null) {
            B b = new B();
            b.setA(a);
            h = new Holder(b);
            session.setAttribute(B.class.getName(), h);
        }
        return (B) h.held;
    }

    public void destroy() {
    }

    // So that other servlets/filters can't access session.attributes directly for this
    // An IoC thing.
    private static class Holder {
        private Holder(Object held) {
            this.held = held;
        }

        private Object held;
    }
}
