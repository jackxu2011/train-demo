package com.linkcld.cepc.idworker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @since 2019-03-11
 */
@Component
@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperty {

    private Long workerId;
}
