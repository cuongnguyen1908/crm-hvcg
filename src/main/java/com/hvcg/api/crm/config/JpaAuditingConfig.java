package com.hvcg.api.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {


        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
            Optional<String> optional = Optional.of(authentication.getName());
            if (authentication == null) {
                return null;
            }
            return optional;
        }
    }
}
