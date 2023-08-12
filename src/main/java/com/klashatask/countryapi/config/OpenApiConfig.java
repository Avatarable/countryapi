package com.klashatask.countryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
    @Value("${app.dev_url}")
    private String devUrl;

    @Bean
    public OpenAPI qrDemoOpenApi(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Environment Countries API");

        Contact contact = new Contact();
        contact.setName("Able");
        contact.setEmail("avatarable@gmail.com.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("COUNTRY QUERY API")
                .version("1.0")
                .contact(contact)
                .description("This is an API that exposes various endpoints to get country infos")
                .license(mitLicense);
        List<Server> servers = new ArrayList<>();
        servers.add(devServer);
        return new OpenAPI().info(info).servers(servers);
    }
}
