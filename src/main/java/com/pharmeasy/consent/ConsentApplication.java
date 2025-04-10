package com.pharmeasy.consent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@SpringBootApplication
//@EnableAspectJAutoProxy
//@EnableLoadTimeWeaving
@EnableCaching
@EnableAsync
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class ConsentApplication {

    public static void main(String[] args) {
        log.info("Starting Consent Management Application...");
        SpringApplication.run(ConsentApplication.class, args);
        log.info("Consent Management Application started successfully.");
    }
}
