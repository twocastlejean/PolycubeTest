package kr.co.polycube.backendtest.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class SpecialCharacterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("[SpecialCharacterFilter.doFilter]");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        log.info("Filtering requestURI: {}", requestURI);

        String queryString = httpRequest.getQueryString() != null ? URLDecoder.decode(httpRequest.getQueryString(), StandardCharsets.UTF_8) : "";
        log.info("Filtering queryString: {}", queryString);


        if (!isValidURL(requestURI) || !isValidURL(queryString)) {
            log.warn("Invalid requestURI: {}", requestURI);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: No Special Character Allowed");

            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValidURL(String url) {
        return !url.matches(".*[^\\w&=?/:].*");
    }
}
