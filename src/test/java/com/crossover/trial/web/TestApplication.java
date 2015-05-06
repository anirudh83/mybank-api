package com.crossover.trial.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

/**
 * Created by anirudh on 04/05/15.
 */


@SpringBootApplication
@ComponentScan(basePackages = {"com.crossover.trial"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { PropertiesTestConfigurer.class})
public class TestApplication {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}