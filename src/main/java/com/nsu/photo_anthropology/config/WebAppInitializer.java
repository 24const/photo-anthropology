package com.nsu.photo_anthropology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();

        context.register(WebConfig.class);
        context.setConfigLocation("com.nsu.photo_anthropology.config");

        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        Path path = null;
        try {
            path = Files.createTempDirectory("target");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int MAX_UPLOAD_SIZE = 1024 * 1024 * 1024;

     // ToDo: Тут магия, нужно разобраться с установкой размеров
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(path.toString(),
                MAX_UPLOAD_SIZE*1024*1024, MAX_UPLOAD_SIZE * 1024*1024, MAX_UPLOAD_SIZE*1024*1024);

        dispatcher.setMultipartConfig(multipartConfigElement);
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
