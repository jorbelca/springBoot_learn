package com.thehecklers.planefinder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, Environment env) {
        boolean hasIssuer = env.containsProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri")
                || env.containsProperty("spring.security.oauth2.resourceserver.jwt.jwk-set-uri");

        var config = http.csrf().disable().authorizeExchange(ex -> ex.anyExchange().permitAll());

        if (hasIssuer) {
            config = http.csrf().disable().authorizeExchange(ex -> ex.anyExchange().authenticated())
                    .oauth2ResourceServer(o -> o.jwt());
        }
        return config.build();
    }
}