package com.example.oauth2authoricationserver.config;

import com.example.oauth2authoricationserver.entity.User;
import com.example.oauth2authoricationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//순서는 : AuthenticationManager -> AuthenticationProvider -> UserDetailsService -> AuthenticationProvider -> AuthenticationManager
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUid(name).orElseThrow(() -> new UsernameNotFoundException("user is not exists")); //username 확인
        if (!passwordEncoder.matches(password, user.getPassword())){ //password 확인
            throw new BadCredentialsException("password is not valid");
        }
        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
