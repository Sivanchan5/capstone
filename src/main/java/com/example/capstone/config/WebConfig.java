package com.example.capstone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Web configuration class for handling static resource mapping.
 * This class configures the mapping of URL paths to specific resource locations,
 * allowing files (such as uploaded images) to be served directly from the filesystem.
 *
 * Implements {@link WebMvcConfigurer} to provide custom configurations for Spring MVC.
 *
 * @author Xiwen Chen
 * @date Dec 31, 2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Configures the handling of static resources.
     * Maps the URL path "/uploads/**" to the physical directory
     * "src/main/resources/static/uploads/", allowing uploaded files to be accessed via HTTP.
     *
     * @param registry ResourceHandlerRegistry to register custom resource handler mappings.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/resources/static/uploads/");
    }
}
