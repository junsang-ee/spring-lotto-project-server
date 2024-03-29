package com.lotto.web.config;

import com.lotto.web.config.jwt.JwtAuthenticationFilter;
import com.lotto.web.config.jwt.JwtTokenProvider;
import com.lotto.web.security.ExtendedUserDetailsService;
import com.lotto.web.security.UserDetailServiceImpl;
import com.lotto.web.service.UserService;
import com.lotto.web.util.BeanSuppliers;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final ApplicationContext context;

    public static final String[] PERMIT_ANT_PATH = {
        "/api/auth/**"
    };

    public static final String[] PERMIT_ANT_ONLY_GET_PATH = {
        "/api/lotto/random",
        "/api/board",
        "/api/board/{boardId}/post"
    };

    public static final String[] ADMIN_ANT_PATH = {
        "/api/admin/**"
    };

    public static final String[] TEST_PATH = {
        "/api/lotto/**",
        "/api/board/**",
        "/api/user/**",
        "/api/post/**",
        "/api/reply/**"
    };

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers(PERMIT_ANT_PATH).permitAll()
                .antMatchers(ADMIN_ANT_PATH).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new JwtAuthenticationFilter(BeanSuppliers.beanSupplier(context, JwtTokenProvider.class)), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Primary
    public ExtendedUserDetailsService userDetailsService() {
        return new UserDetailServiceImpl(BeanSuppliers.beanSupplier(context, UserService.class));
    }



}
