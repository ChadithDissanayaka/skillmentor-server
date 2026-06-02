package com.skillmentor.root.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartupLogger {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @PostConstruct
    public void logStartupInfo() {

        log.info("========================================");
        log.info("Skill Mentor Application Started");
        log.info("Active Profile : {}", activeProfile);
        log.info("Datasource     : {}", datasourceUrl);
        log.info("========================================");
    }
}