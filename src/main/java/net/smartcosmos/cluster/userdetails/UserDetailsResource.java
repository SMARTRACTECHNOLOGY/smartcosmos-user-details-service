package net.smartcosmos.cluster.userdetails;

import java.io.IOException;
import java.util.Collections;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.smartcosmos.cluster.userdetails.domain.AuthenticateRequest;
import net.smartcosmos.cluster.userdetails.domain.UserAuthenticationProperties;
import net.smartcosmos.cluster.userdetails.domain.UserDto;

@RestController
@Slf4j
public class UserDetailsResource {

    private PasswordEncoder passwordEncoder;
    private UserAuthenticationProperties userAuthenticationProperties;

    @Autowired
    public UserDetailsResource(PasswordEncoder passwordEncoder, UserAuthenticationProperties userAuthenticationProperties) {

        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationProperties = userAuthenticationProperties;
    }

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public UserDto authenticate(@RequestBody @Valid AuthenticateRequest authentication)
        throws UsernameNotFoundException, IOException {

        log.info("Requested information on username {} with {}", authentication.getName(),
                 authentication);

        String passwordHash = null;
        if (StringUtils.equals(userAuthenticationProperties.getName(), authentication.getPrincipal())) {
            if (!StringUtils.equals(userAuthenticationProperties.getPassword(), authentication.getCredentials())) {
                log.error("Password incorrect.");
                throw new BadCredentialsException("Invalid password");
            }
            passwordHash = passwordEncoder.encode(authentication.getCredentials());
        }

        return UserDto.builder()
            .tenantUrn(userAuthenticationProperties.getTenantUrn())
            .userUrn(userAuthenticationProperties.getUserUrn())
            .username(authentication.getName())
            .passwordHash(passwordHash)
            .authorities(
                userAuthenticationProperties.getAuthorities() != null ? userAuthenticationProperties.getAuthorities() : Collections.emptyList())
            .build();
    }

}
