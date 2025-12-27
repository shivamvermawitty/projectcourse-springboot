package com.projectcourse.projectcourse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AppConfig {
    
    @Value("${app.jwt.secret:jwt_secret_key}")
    private String jwtSecret;
    
    @Value("${app.jwt.expiration:3600}")
    private long jwtExpiration;
}
