package com.xxl.job.admin.core.conf;

import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfig {

    @Value("${spring.dubbo.application.name}")
    private String applicationName;

    @Value("${spring.dubbo.registry.id}")
    private String registryId;

    @Value("${spring.dubbo.registry.protocol}")
    private String registryProtocol;

    @Value("${spring.dubbo.registry.address}")
    private String registryAddress;

    @Value("${spring.dubbo.registry.port}")
    private int registryPort;

    @Bean
    public RegistryConfig handlerRegistryConfig() {
        RegistryConfig handlerRegistryConfig = new RegistryConfig();
        //初始化Config
        handlerRegistryConfig.setId(registryId);
        handlerRegistryConfig.setProtocol(registryProtocol);
        handlerRegistryConfig.setAddress(registryAddress);
        handlerRegistryConfig.setPort(registryPort);
        handlerRegistryConfig.setCheck(false);
        return handlerRegistryConfig;
    }

}
