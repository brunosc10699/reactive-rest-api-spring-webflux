package com.bruno.bootcampheroes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.bruno.bootcampheroes.constants.HeroConstant.HERO_URN;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment environment;

    private static final String[] PUBLIC_MATCHERS_GET = {
            HERO_URN + "/**"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .cors()
                .and()
                .csrf()
                .disable();
        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            serverHttpSecurity
                    .authorizeExchange()
                    .anyExchange()
                    .permitAll();
        } else {
            serverHttpSecurity
                    .authorizeExchange()
                    .pathMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
                    .permitAll()
                    .anyExchange()
                    .authenticated();
        }
        return serverHttpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
