package net.smartcosmos.cluster.userdetails.domain;

import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@Data
@ConfigurationProperties("smartcosmos.security.user")
public class UserAuthenticationProperties {

    private String name;
    private String password;
    private List<String> authorities;

}
