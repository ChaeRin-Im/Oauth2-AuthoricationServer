package com.example.oauth2authoricationserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //AuthenticationManager 생성시 직접 커스텀한 authenticationProvider 세팅
        auth.authenticationProvider(authenticationProvider);

        /*테스트용
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("{bcrypt}$2a$10$ADx.uVzQfWHYsSdVQFYtcuPiPTUsdHiFaa4bQzqjzL8mb7tP7dhuC")
                .roles("USER");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
