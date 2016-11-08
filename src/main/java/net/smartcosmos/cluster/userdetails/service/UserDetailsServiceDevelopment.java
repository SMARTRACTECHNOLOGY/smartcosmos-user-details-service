package net.smartcosmos.cluster.userdetails.service;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.smartcosmos.cluster.userdetails.config.UserDetailsDevelopmentServiceProperties;
import net.smartcosmos.cluster.userdetails.domain.ConfiguredUserDetails;
import net.smartcosmos.userdetails.domain.UserDetails;
import net.smartcosmos.userdetails.service.UserDetailsService;

/**
 * User Details Service implementation that uses hard-coded user details from the configuration.
 */
@Slf4j
@Service
public class UserDetailsServiceDevelopment implements UserDetailsService {

    private final UserDetailsDevelopmentServiceProperties userDetails;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;

    @Autowired
    public UserDetailsServiceDevelopment(
        ConversionService conversionService,
        UserDetailsDevelopmentServiceProperties userDetails,
        PasswordEncoder passwordEncoder,
        Validator validator) {

        this.passwordEncoder = passwordEncoder;
        this.userDetails = userDetails;
        this.validator = validator;
        this.conversionService = conversionService;
    }

    @Override
    public UserDetails getUserDetails(String username, String password) throws IllegalArgumentException, AuthenticationException {

        Assert.isTrue(StringUtils.isNotBlank(username), "username may not be blank");
        Assert.isTrue(StringUtils.isNotBlank(password), "password may not be blank");

        ConfiguredUserDetails user = userDetails.getUsers().get(username);

        if (user == null || !StringUtils.equals(user.getUsername(), username)) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        if (!StringUtils.equals(user.getPassword(), password)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return conversionService.convert(user, UserDetails.class);
    }

    @Override
    public boolean isValid(UserDetails userDetails) {

        log.debug("Entity: {}", userDetails);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        log.debug("Constraint violations: {}", violations.toString());

        return violations.isEmpty();
    }
}
