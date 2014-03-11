package com.clowndata.rest.interceptors;

import com.clowndata.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class DevelopmentFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    Configuration configuration;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(configuration.isTest()) {
            logger.warn("Using Access-Control-Allow-Origin: *");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(configuration.isTest()) {
            addDevelopmentHeaders(servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addDevelopmentHeaders(ServletResponse servletResponse) {
        if(servletResponse instanceof HttpServletResponse) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    @Override
    public void destroy() {

    }
}
