package net.smartcosmos.cluster.userdetails.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "net.smartcosmos.cluster.userdetails")
@EnableConfigurationProperties(UserDetailsDevelopmentServiceProperties.class)
public class UserDetailsDevelopmentServiceTestConfiguration {
}
