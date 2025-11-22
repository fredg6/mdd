package com.orion.mdd.config;

import com.orion.mdd.security.service.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        var auditor = ((CustomUserDetails) authentication.getPrincipal()).getUsername();

        return Optional.of(auditor);
    }
}
