package com.sample.services;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CORSFilter implements CorsConfigurationSource {


    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("x-requested-with");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("/**"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("x-requested-with", "Authorization"));

        return corsConfiguration;
    }
}
