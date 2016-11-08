package net.smartcosmos.cluster.userdetails.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = { "net.smartcosmos.cluster.userdetails" })
public class UserDetailsDevelopmentServiceTestConfiguration {
}
