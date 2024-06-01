package com.cinema.sso.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Cinema API",
        version = "1.0",
        description = "Documentation for Cinema API v1.0"
    ),
   servers = {
        @Server(url = "http://localhost:8080/api/v1", description = "Local ENV"),
        @Server(url = "https://cinema-api.com", description = "Production ENV")
    }
)

@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Token", 
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
  
}
