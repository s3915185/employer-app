package org.example;

import org.example.interceptor.ErrorHandlingInterceptor;
import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.h2.server.web.WebServlet;

@SpringBootApplication(scanBasePackages = "org.example")
@PropertySource({"classpath:/application.properties"})
public class ApplicationBackendConfig extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationBackendConfig.class);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/h2console/*");
        return registrationBean;
    }
    @Bean
    public GlobalServerInterceptorConfigurer globalServerInterceptorConfigurer() {
        return interceptors -> interceptors.add(new ErrorHandlingInterceptor());
    }
}
