package net.smartcosmos.cluster.userdetails.converter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;
import net.smartcosmos.userdetails.domain.UserDetails;

@Component
public class UserAuthenticationPropertiesToUserDetailsConverter implements Converter<UserAuthenticationProperties, UserDetails> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationPropertiesToUserDetailsConverter(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails convert(UserAuthenticationProperties source) {

        return UserDetails.builder()
            .tenantUrn(source.getTenantUrn())
            .userUrn(source.getUserUrn())
            .username(source.getName())
            .passwordHash(passwordEncoder.encode(source.getPassword()))
            .authorities(source.getAuthorities() != null ? source.getAuthorities() : Collections.emptyList())
            .build();
    }
}
