package com.nsu.photo_anthropology.config_workers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.nsu.photo_anthropology")
public class WebMvcConfig {
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("src/main/webapp/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
