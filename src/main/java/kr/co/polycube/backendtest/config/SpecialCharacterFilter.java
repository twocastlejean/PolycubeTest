package kr.co.polycube.backendtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.polycube.backendtest.exception.InvalidURLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SpecialCharacterFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("[SpecialCharacterFilter.doFilter]");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        log.info("Filtering requestURI: {}", requestURI);

        String queryString = httpRequest.getQueryString() != null ? URLDecoder.decode(httpRequest.getQueryString(), StandardCharsets.UTF_8) : "";
        log.info("Filtering queryString: {}", queryString);


        if (isValidURL(requestURI) || isValidURL(queryString)) {
            log.warn("Invalid requestURI: {}", httpRequest.getRequestURL().toString());
            //throw new InvalidURLException("Invalid URL - No Special Character Allowed: " + httpRequest.getRequestURL().toString());

            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("reason", "Invalid URL - No Special Character Allowed: " + httpRequest.getRequestURL().toString());
            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValidURL(String url) {
        return url.matches(".*[^\\w&=?/:].*");
    }
}
