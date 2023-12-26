package com.example.securityformlogin.services.auth;

import com.example.securityformlogin.models.CustomUserDetails;
import com.example.securityformlogin.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails user = jpaUserDetailsService.loadUserByUsername(username);

        switch (user.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    private Authentication checkPassword(CustomUserDetails user, String password, PasswordEncoder encoder) {
        if (encoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
        else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}