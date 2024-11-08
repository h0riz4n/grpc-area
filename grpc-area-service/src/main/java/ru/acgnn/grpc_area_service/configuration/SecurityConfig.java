package ru.acgnn.grpc_area_service.configuration;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import ru.acgnn.grpc_area_service.converter.GrantedAuthorityConverter;
import ru.acgnn.grpc_area_service.property.OAuth2ResourceServerProperty;
import ru.acgnn.grpc_area_service.property.OAuth2ResourceServerProperty.Issuer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final OAuth2ResourceServerProperty resourceServerProperty;

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
            .sessionManagement(sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> oauth2.authenticationManagerResolver(jwtIssuerAuthenticationManagerResolver()))
            .build();
    }

    @Bean
    JwtIssuerAuthenticationManagerResolver jwtIssuerAuthenticationManagerResolver() {
        Map<String, AuthenticationManager> authenticationManagers = resourceServerProperty.getJwt().stream()
            .collect(Collectors.toMap(
                issuer -> issuer.getIssuerUri(),
                issuer -> jwtAuthProvider(issuer)::authenticate
            ));
        return new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);
    }

    @Bean
    AuthenticationManager authManager() {
        return new ProviderManager(resourceServerProperty.getJwt().stream().map(this::jwtAuthProvider).collect(Collectors.toList()));
    }

    @Bean
    BearerAuthenticationReader authReader() {
        return new BearerAuthenticationReader(accessToken -> new BearerTokenAuthenticationToken(accessToken));
    }

    private JwtDecoder jwtDecoderByIssuerUri(String issuerUri) {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    private JwtAuthenticationProvider jwtAuthProvider(Issuer issuer) {
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoderByIssuerUri(issuer.getIssuerUri()));
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthorityConverter());
        authenticationProvider.setJwtAuthenticationConverter(jwtAuthenticationConverter);
        return authenticationProvider;
    }
}
