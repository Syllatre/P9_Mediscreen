package com.mediscreen.patient.configTest;

import com.mediscreen.patient.configuration.StringToDateConverter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
public class ConfigurationTest implements WebMvcConfigurer {
    private final StringToDateConverter stringToDateConverter;

    public ConfigurationTest(StringToDateConverter stringToDateConverter) {
        this.stringToDateConverter = stringToDateConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToDateConverter);
    }
}
