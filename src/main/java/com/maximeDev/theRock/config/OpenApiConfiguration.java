package com.maximeDev.theRock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("BrainWave")
                .description("Pomme d'API")
                .version("1.0")
                .contact(apiContact())
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("Unlicenced")
                .url("https://google.com");
    }

    private Contact apiContact() {
        return new Contact()
                .name("BRAINWAVE")
                .email("HELENE@GOOGLE.COM")
                .url("https://github.com/WildCodeSchool/0523-RemFr-JavaAngular-P2-backend-brainwave.git");
    }
}
