package org.hasadna.bus.config;

import io.micrometer.core.instrument.Clock;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Duration;

@Configuration
public class MonitorConfig {

    @Autowired
    private Environment env;

    @Bean
    DatadogMeterRegistry registry() {
        DatadogConfig config = new DatadogConfig() {
            @Override
            public Duration step() {
                return Duration.ofSeconds(30);
            }

            @Override
            public String get(String k) {
                k = "management.metrics.export." + k;
                return (env.containsProperty(k)) ?
                        env.getProperty(k) :
                        null;
            }
        };
        DatadogMeterRegistry registry = new DatadogMeterRegistry(config, Clock.SYSTEM);
        return registry;
    }
}