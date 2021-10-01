package com.example.oauth2authoricationserver.config;

import com.example.oauth2authoricationserver.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/*
 * 참고 URL: https://www.skyer9.pe.kr/wordpress/?p=2294
 */

//인증 서버를 구현 (토큰 발급 / 토큰 리프레시)
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.jwt.signkey}")
    private String signKey;

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; //스프링 시큐리티의 핵심 인터페이스(얘가 인증을 많이 함)
    private final CustomUserDetailService userDetailService;

    //ClientDetailsServiceConfigurer: client detail service를 정의하는 구성 클래스
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //JDBC 방식
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

        //inMemory 방식
        /*clients.inMemory()
                .withClient("foo")
                .secret(passwordEncoder.encode("bar"))
                .redirectUris("http://localhost:8080/test/auth")
                //기본 인증 5개, 여기 속성이 없으면 인증 불가
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .scopes("read", "write", "email", "profile")
                .accessTokenValiditySeconds(30000);*/
    }

    //AuthorizationServerSecurityConfigurer: 토큰 엔드포인트에 대한 보안 제약을 정의
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token, 토큰체크 엔드포인트 활성화 해줌
                .allowFormAuthenticationForClients();
    }

    //AuthorizationServerEndpointsConfigurer: 인가와 토큰 엔드포인트, 토큰 서비스를 정의
    // AuthenticationManager, TokenStore, UserDetailsService를 설정할 수 있음
    // 유저정보를 확인을 해야 토큰을 발급 받을 수 있음
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //JWT 방식
        /*super.configure(endpoints);
        endpoints
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailService);*/

        //JDBC 방식
        /*endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(new JdbcTokenStore(dataSource));*/
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signKey);
        return converter;
    }



}
