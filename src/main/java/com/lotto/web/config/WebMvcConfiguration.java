package com.lotto.web.config;

import com.lotto.web.config.converter.StringToInstantConverter;
import com.lotto.web.config.converter.StringToLocalDateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .maxAge(3600L);
    }

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(String.class, Instant.class, new StringToInstantConverter());
        registry.addConverter(String.class, LocalDate.class, new StringToLocalDateConverter());
    }
}
