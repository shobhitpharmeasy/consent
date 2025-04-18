package com.pharmeasy.consent.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${springdoc.title:PharmEasy Consent API}")
    private final String title = "";

    @Value("${springdoc.version:1.0.0}")
    private final String version = "";

    @Value(
        "${springdoc.description:API documentation for the PharmEasy Consent " +
        "Service}"
    )
    private final String description = "";

    @Value("${springdoc.terms-of-service-url:https://pharmeasy.in/terms}")
    private final String termsOfServiceUrl = "";

    @Value("${springdoc.contact.name:PharmEasy Tech Team}")
    private final String contactName = "";

    @Value("${springdoc.contact.url:https://pharmeasy.in}")
    private final String contactUrl = "";

    @Value("${springdoc.contact.email:techsupport@pharmeasy.in}")
    private final String contactEmail = "";

    @Value("${springdoc.license.name:Apache 2.0}")
    private final String licenseName = "";

    @Value(
        "${springdoc.license.url:https://www.apache.org/licenses/LICENSE-2.0}"
    )
    private final String licenseUrl = "";

    @Bean
    public OpenAPI customOpenAPI() {
        final Contact contact =
            new Contact().name(contactName).url(contactUrl).email(contactEmail);

        final License license = new License().name(licenseName).url(licenseUrl);

        final Info info = new Info()
                              .title(title)
                              .version(version)
                              .description(description)
                              .termsOfService(termsOfServiceUrl)
                              .contact(contact)
                              .license(license)
            ;

        return new OpenAPI()
                   .info(info)
                   .servers(List.of(
                       new Server()
                           .url("http://localhost:8080")
                           .description("Local Development Server"),
                       new Server()
                           .url("https://api.pharmeasy.in")
                           .description("Production Server")
                                   ))
                   .externalDocs(new ExternalDocumentation()
                                     .description("PharmEasy API Documentation")
                                     .url("https://docs.pharmeasy.in"));
    }
}
