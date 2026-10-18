package com.securitydemo.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthEntryPointJWT implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJWT.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {},", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);// changing the content type to JSON
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ///getting the info for the authentication body
        final Map<String,Object> body = new HashMap<>();
        body.put("Status :",HttpServletResponse.SC_UNAUTHORIZED);
        body.put("Error :","Unauthorized");
        body.put("Message :",authException.getMessage());
        body.put("Path :",request.getServletPath());

        ///Creating the Jason to get all the details
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),body);
    }
}
