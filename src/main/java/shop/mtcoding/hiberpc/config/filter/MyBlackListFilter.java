package shop.mtcoding.hiberpc.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyBlackListFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // x-www-form-urlencoded
        String value = request.getParameter("value");
        if (value.equals("babo")) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("당신은 블랙리스트가 되었습니다");
            return;
        }
        chain.doFilter(request, response);
    }
}
