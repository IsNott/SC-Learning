package com.nott.cloud.hystrixservice.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Nott
 * @Date 2022/7/12 10:15
 */

@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class HystrixFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext context = null;
        try {
            //解决Hystrix的缓存功能不生效的问题
            context = HystrixRequestContext.initializeContext();
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
