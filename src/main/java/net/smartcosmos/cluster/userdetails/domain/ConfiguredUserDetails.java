package net.smartcosmos.cluster.userdetails.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User details that are configured in the yaml configuration.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfiguredUserDetails {

    private String username;
    private String password;
    private List<String> authorities;
    private String tenantUrn;
    private String userUrn;

}
