package ru.cardinalnsk.springjavajuniortest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Mobile payment",
        version = "v0.1"
    ),
    servers = {
        @Server(
            url = "http://localhost:8089/", description = "Local server"
        )
    }
)
@SecurityScheme(
    name = "Basic",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class SpringJavaJuniorTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJavaJuniorTestApplication.class, args);
    }

}
