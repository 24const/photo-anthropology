package com.nsu.photo_anthropology.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.nsu.photo_anthropology.controllers"})
public class WebConfig {
    //
}