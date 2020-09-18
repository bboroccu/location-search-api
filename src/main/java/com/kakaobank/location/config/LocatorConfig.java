package com.kakaobank.location.config;

import com.kakaobank.location.service.component.LocationSearcherManagerFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocatorConfig {
    @Bean
    public ServiceLocatorFactoryBean LocationSearchApiManagerFactory() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(LocationSearcherManagerFactory.class);
        return factoryBean;
    }
}
