package net.smartcosmos.cluster.userdetails;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;

@SpringBootApplication
@Controller
@EnableDiscoveryClient
@Slf4j
@EnableConfigurationProperties({ UserAuthenticationProperties.class })
public class DevelopmentUserDetailsService {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DevelopmentUserDetailsService.class).web(true)
            .run(args);
    }
}
