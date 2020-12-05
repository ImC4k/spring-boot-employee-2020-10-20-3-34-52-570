package com.thoughtworks.springbootemployee.config;

import com.moesif.servlet.MoesifFilter;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
public class IncomingRequestInterceptor implements WebMvcConfigurer {
    @Value("${api.management.key}") String managementKey;
    @Bean
    public Filter moesifFilter() {
        return new MoesifFilter(managementKey);
    }
}