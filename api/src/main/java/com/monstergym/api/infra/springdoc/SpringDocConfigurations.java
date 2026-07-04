package com.monstergym.api.infra.springdoc;

import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                            .title("MonsterGym_API")
                            .description("A RESTful API for gym management built with Java and Spring Boot. \" +\n" +
                                    "                                    \"The system handles student and trainer registration with full CRUD operations, class scheduling with business rule validation, \" +\n" +
                                    "                                    \"membership payments, physical assessments with automatic trainer matching, business statistics, \" +\n" +
                                    "                                    \"and stateless JWT-based authentication with role-based access control.\"")
                            .contact(new Contact()
                                    .name("Author")
                                    .email("thiagoshimizusodre@gmail.com")));
    }

}
