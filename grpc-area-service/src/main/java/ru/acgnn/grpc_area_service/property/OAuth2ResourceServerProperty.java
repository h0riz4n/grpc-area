package ru.acgnn.grpc_area_service.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
public class OAuth2ResourceServerProperty {

    List<Issuer> jwt;
    
    @Getter
    @Setter
    @ToString
    public static class Issuer {

        private String jwkSetUri;

		/**
		 * JSON Web Algorithms used for verifying the digital signatures.
		 */
		private List<String> jwsAlgorithms = Arrays.asList("RS256");

		/**
		 * URI that can either be an OpenID Connect discovery endpoint or an OAuth 2.0
		 * Authorization Server Metadata endpoint defined by RFC 8414.
		 */
		private String issuerUri;

		/**
		 * Location of the file containing the public key used to verify a JWT.
		 */
		private Resource publicKeyLocation;

		/**
		 * Identifies the recipients that the JWT is intended for.
		 */
		private List<String> audiences = new ArrayList<>();

		/**
		 * Prefix to use for authorities mapped from JWT.
		 */
		private String authorityPrefix;

		/**
		 * Regex to use for splitting the value of the authorities claim into authorities.
		 */
		private String authoritiesClaimDelimiter;

		/**
		 * Name of token claim to use for mapping authorities from JWT.
		 */
		private String authoritiesClaimName;

		/**
		 * JWT principal claim name.
		 */
		private String principalClaimName;
    }

}

