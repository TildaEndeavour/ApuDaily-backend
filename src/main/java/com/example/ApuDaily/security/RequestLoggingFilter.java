package com.example.ApuDaily.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(
        name="logging.filter.enabled",
        havingValue="true",
        matchIfMissing = false
)
public class RequestLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logRequest(httpRequest);
        long startTime = System.currentTimeMillis();
        try{
            chain.doFilter(request,response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logResponse(httpRequest,httpResponse,duration);
        }

    }

    private void logRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        log.info("Request: {} - {}", method, requestURI);
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, long duration){
        int statusCode = response.getStatus();
        log.info("Response: HTTP {} - {}, time: {}ms", statusCode, request.getRequestURI(), duration);
    }
}
