package net.smartcosmos.cluster.userdetails.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration properties for dummy user.
 */
@Data
@ConfigurationProperties("smartcosmos.user")
public class UserAuthenticationProperties {

    private String name = "user";
    private String password = "password";
    private List<String> authorities = new ArrayList<>();
    private String tenantUrn = "urn:tenant:uuid:513161AA-24B0-410F-BE1D-D6CD04B6C9BB";
    private String userUrn = "urn:user:uuid:71266402-55BC-4267-B56E-407BE9778C3B";
}
