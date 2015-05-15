package com.magr.lolrest.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.validation.constraints.NotNull;

@Configuration
@ComponentScan(basePackages = { "com.magr.lolrest.*" })
@PropertySource("classpath:lol_rest.properties")
public class LolProperties {

    @Value("${lol.rest.basePath}")
    private String basePath;

    @Value("${lol.rest.apiKey}")
    private String apiKey;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUrlWithKey() {
        return this.getBasePath() + "?api_key=" + this.getApiKey();
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}