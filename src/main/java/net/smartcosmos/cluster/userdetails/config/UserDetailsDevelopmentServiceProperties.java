package net.smartcosmos.cluster.userdetails.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.smartcosmos.cluster.userdetails.domain.ConfiguredUserDetails;

/**
 * Configuration properties for dummy user.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ConfigurationProperties("smartcosmos.security")
public class UserDetailsDevelopmentServiceProperties {

    public static final String DEFAULT_USER_NAME = "user";
    public static final String DEFAULT_PASSWORD = "password";
    public static final List<String> DEFAULT_AUTHORITIES = new ArrayList<>();
    public static final String DEFAULT_USER_URN = "urn:user:uuid:71266402-55BC-4267-B56E-407BE9778C3B";
    public static final String DEFAULT_TENANT_URN = "urn:tenant:uuid:513161AA-24B0-410F-BE1D-D6CD04B6C9BB";

    private static final ConfiguredUserDetails defaultUserDetails = ConfiguredUserDetails.builder()
        .tenantUrn(DEFAULT_TENANT_URN)
        .userUrn(DEFAULT_USER_URN)
        .username(DEFAULT_USER_NAME)
        .password(DEFAULT_PASSWORD)
        .authorities(DEFAULT_AUTHORITIES)
        .build();

    private Map<String, ConfiguredUserDetails> users = new HashMap<>();

}
