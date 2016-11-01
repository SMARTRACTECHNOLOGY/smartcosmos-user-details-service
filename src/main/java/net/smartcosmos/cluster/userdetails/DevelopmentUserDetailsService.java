package net.smartcosmos.cluster.userdetails;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ UserAuthenticationProperties.class })
public class DevelopmentUserDetailsService {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DevelopmentUserDetailsService.class).web(true)
            .run(args);
    }
}
