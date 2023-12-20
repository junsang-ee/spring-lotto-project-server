package com.lotto.web.config;

import com.lotto.web.config.jwt.JwtAuthenticationFilter;
import com.lotto.web.config.jwt.JwtTokenProvider;
import com.lotto.web.util.BeanSuppliers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final ApplicationContext context;

    public static final String[] PERMIT_ANT_PATH = {
        "/api/auth/**",
        "/api/post/**",
        "/api/reply/**"
    };

    public static final String[] ADMIN_ANT_PATH = {
        "/api/admin/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers(PERMIT_ANT_PATH).permitAll()
                .antMatchers(ADMIN_ANT_PATH).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().cors()
                .and().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new JwtAuthenticationFilter(BeanSuppliers.beanSupplier(context, JwtTokenProvider.class)), UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}
