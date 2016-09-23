package net.smartcosmos.cluster.userdetails.config;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for dummy user.
 */
@Data
@ConfigurationProperties("smartcosmos.security.user")
public class UserAuthenticationProperties {

    public static final String DEFAULT_NAME = "user";
    public static final String DEFAULT_PASSWORD = "password";
    public static final Collection<String> DEFAULT_AUTHORITIES = new ArrayList<>();
    public static final String DEFAULT_USER_URN = "urn:user:uuid:71266402-55BC-4267-B56E-407BE9778C3B";
    public static final String DEFAULT_TENANT_URN = "urn:tenant:uuid:513161AA-24B0-410F-BE1D-D6CD04B6C9BB";

    private String name = DEFAULT_NAME;
    private String password = DEFAULT_PASSWORD;
    private Collection<String> authorities = DEFAULT_AUTHORITIES;
    private String tenantUrn = DEFAULT_TENANT_URN;
    private String userUrn = DEFAULT_USER_URN;
}
