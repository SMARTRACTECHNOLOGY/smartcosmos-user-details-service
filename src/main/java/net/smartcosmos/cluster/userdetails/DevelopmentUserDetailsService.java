package net.smartcosmos.cluster.userdetails;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import net.smartcosmos.cluster.userdetails.config.UserDetailsDevelopmentConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
@Import(UserDetailsDevelopmentConfiguration.class)
public class DevelopmentUserDetailsService {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DevelopmentUserDetailsService.class).web(true)
            .run(args);
    }
}
