package com.linkcld.cepc.idworker.config;

import com.linkcld.cepc.idworker.service.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since 2019-03-11
 */
@Configuration
public class BeanConfig {

    @Autowired
    private SnowflakeProperty snowflakeProperty;

    @Bean
    IdWorker idWorker() {
        return IdWorker.getIdWorker(snowflakeProperty.getWorkerId());
    }

}
