package com.yichen.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author dengbojing
 */
@Component
@Configuration
public class AuditorAwareAutoConfigure implements AuditorAware<String> {
    /**
     * @return 调用人
     */
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("system");
    }
}
