package br.com.uanderson.api_rest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration// Anotação para definir que esta é uma classe de configuração
@EnableWebSecurity// Anotação para habilitar a segurança web
@RequiredArgsConstructor// Anotação para gerar um construtor com os atributos finais (final)
public class SecurityConfig {

    // Definição de um bean que configura a cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuração das autorizações HTTP
        http.authorizeHttpRequests(auth -> {
                    // Permite acesso irrestrito(total permissão) aos endpoints que contém "swagger-ui", "api-docs" ou "swagger-resources"
                    auth.requestMatchers(request ->
                            request.getRequestURI().contains("swagger-ui") ||
                                    request.getRequestURI().contains("api-docs") ||
                                    request.getRequestURI().contains("swagger-resources")
                    ).permitAll();
                    // Requer autenticação para qualquer outra requisição
                    auth.anyRequest().authenticated();
                })
                // Configura o servidor de recursos OAuth2 para utilizar JWT
                .oauth2ResourceServer(configure -> configure.jwt(
                        jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter()))
                );

        // Retorna a configuração da cadeia de filtros de segurança construída
        return http.build();
    }

    // Método que cria um conversor de JWT para autenticação
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
        // Instancia um conversor de autenticação JWT
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        // Define um conversor customizado para as autoridades do JWT
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRealmRoleConverter());

        // Retorna o conversor configurado
        return jwtConverter;
    }

    // Classe interna para converter as roles do Keycloak em GrantedAuthority
    private class KeyCloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        @Override
        public Collection<GrantedAuthority> convert(Jwt source) {
            // Se as claims do JWT forem nulas, retorna uma lista vazia
            if (source.getClaims() == null) {
                return List.of();
            }

            // Obtém o objeto "realm_access" das claims do JWT
            final var obj = source.getClaims().get("realm_access");
            // Verifica se o objeto é um mapa
            if (obj instanceof Map<?, ?> realmAccess) {
                // Obtém as roles do mapa "realm_access"
                final var roles = realmAccess.get("roles");
                // Verifica se as roles são uma lista
                if (roles instanceof List<?> list) {
                    // Converte cada role em uma GrantedAuthority, prefixando com "ROLE_"
                    return list.stream()
                            .map(role -> "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
            }
            // Retorna uma lista vazia se não houver roles
            return List.of();
        }
    } // fim da classe interna

} // fim da classe SecurityConfig
