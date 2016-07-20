package net.smartcosmos.cluster.userdetails.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * DTO representation of serialized {@link UsernamePasswordAuthenticationToken}.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticateRequest {

    private AuthenticateDetails details;

    private List<String> authorities;

    private Boolean authenticated;

    private String principal;

    private String credentials;

    private String name;
}
