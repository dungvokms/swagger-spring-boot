package com.example.demoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
public class DemoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/my")
class MyController {

    @GetMapping
    public String hello(@RequestParam(defaultValue = "vnd") String name) {
        return "Hello " + name;
    }

    @PostMapping
    public Object show(@RequestBody Map<String, Object> person) {
        return person;
    }
}

@Configuration
@EnableSwagger2
class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("my-api")
                .select()
                .paths(regex("/api/.*"))
                .build();
    }
}
