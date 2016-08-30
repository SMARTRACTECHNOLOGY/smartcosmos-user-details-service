package net.smartcosmos.cluster.userdetails.domain;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for dummy user.
 */
@Data
@ConfigurationProperties("smartcosmos.security.user")
public class UserAuthenticationProperties {

    private String name = "user";
    private String password = "password";
    private List<String> authorities = Arrays.asList("https://authorities.smartcosmos.net/things/create");

}
