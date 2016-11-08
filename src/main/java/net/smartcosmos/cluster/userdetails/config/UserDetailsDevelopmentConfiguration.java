package net.smartcosmos.cluster.userdetails.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for reading the config.
 */
@Configuration
@EnableConfigurationProperties(UserDetailsDevelopmentServiceProperties.class)
public class UserDetailsDevelopmentConfiguration {

}
