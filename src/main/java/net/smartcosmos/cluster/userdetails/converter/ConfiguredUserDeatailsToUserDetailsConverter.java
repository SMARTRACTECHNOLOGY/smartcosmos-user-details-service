package net.smartcosmos.cluster.userdetails.converter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.smartcosmos.cluster.userdetails.domain.ConfiguredUserDetails;
import net.smartcosmos.userdetails.domain.UserDetails;

@Component
public class ConfiguredUserDeatailsToUserDetailsConverter implements Converter<ConfiguredUserDetails, UserDetails> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ConfiguredUserDeatailsToUserDetailsConverter(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails convert(ConfiguredUserDetails source) {

        return UserDetails.builder()
            .tenantUrn(source.getTenantUrn())
            .userUrn(source.getUserUrn())
            .username(source.getUsername())
            .passwordHash(passwordEncoder.encode(source.getPassword()))
            .authorities(source.getAuthorities() != null ? source.getAuthorities() : Collections.emptyList())
            .build();
    }
}
