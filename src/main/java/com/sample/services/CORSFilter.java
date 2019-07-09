package com.sample.services;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Accept", "application/json");
        response.setHeader("Access-Control-Allow-Origin", "/**");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("X-Requested-With", "XMLHttpRequest");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, " +
                            "Content-Type, Access-Control-Allow-Headers, Authorization");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig){}

    public void destroy(){}
}