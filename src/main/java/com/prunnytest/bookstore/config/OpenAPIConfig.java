package com.prunnytest.bookstore.config;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

//SWAGGER FOR API DOCUMENTATION
@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ridwan Bolanle",
                        email = "7devanle@gmail.com",
                        url = "https://heuy-kt.github.io/My-portFolio/"
                ),
                description = "Book application backend set up",
                title = "BOOK STORE",
                version = "1.0",
                license = @License(
                        name = "Heuy_License",
                        url = "google.com"
                ),
                termsOfService = "Heuy.kt terms of service"
        ),
        servers = {
                @Server(
                        description = "local server",
                        url = "http://localhost:8899"
                ),
                @Server(
                        description = "Production server",
                        url = "http://www.render.com/heuy.kt"
                )
        },
        security = {
//                @SecurityRequirement(
//                        name = "bearerAuth"
//                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {

}