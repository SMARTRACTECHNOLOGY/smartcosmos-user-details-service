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
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;
import net.smartcosmos.cluster.userdetails.domain.UserDetails;

/**
 * User Details Service implementation that uses hard-coded user details from the configuration.
 */
@Slf4j
@Service
public class UserDetailsServiceDevelopment implements UserDetailsService {

    private final ConversionService conversionService;
    private final UserAuthenticationProperties userAuthenticationProperties;
    private final Validator validator;

    @Autowired
    public UserDetailsServiceDevelopment(
        ConversionService conversionService,
        UserAuthenticationProperties userAuthenticationProperties,
        Validator validator) {

        this.conversionService = conversionService;
        this.userAuthenticationProperties = userAuthenticationProperties;
        this.validator = validator;
    }

    @Override
    public UserDetails getUserDetails(String username, String password) throws IllegalArgumentException, AuthenticationException {

        Assert.isTrue(StringUtils.isNotBlank(username), "username may not be blank");
        Assert.isTrue(StringUtils.isNotBlank(password), "password may not be blank");

        if (!username.equals(userAuthenticationProperties.getName())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        if (!password.equals(userAuthenticationProperties.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return conversionService.convert(userAuthenticationProperties, UserDetails.class);
    }

    @Override
    public boolean isValid(UserDetails userDetails) {

        log.debug("Entity: {}", userDetails);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        log.debug("Constraint violations: {}", violations.toString());

        return violations.isEmpty();
    }
}
