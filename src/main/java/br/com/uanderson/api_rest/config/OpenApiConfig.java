package br.com.uanderson.api_rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration// Anotação para definir que esta é uma classe de configuração
public class OpenApiConfig {

    // Injeção dos valores das propriedades definidas no arquivo de configuração do Spring
    @Value("${spring.security.oauth2.resourceserver.endpoint.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.resourceserver.endpoint.token-uri}")
    private String authorizationTokenUri;

    // Definição de um bean para configurar o OpenAPI
    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                // Configuração dos componentes do OpenAPI, especialmente os esquemas de segurança
                .components(
                        new Components()
                                // Adiciona o esquema de segurança OAuth2
                                .addSecuritySchemes("security_auth", new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2) // Define o tipo de esquema como OAuth2
                                        .in(SecurityScheme.In.HEADER) // Define que o token será enviado no cabeçalho
                                        .flows(new OAuthFlows() // Configuração dos fluxos OAuth2
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl(authorizationUri) // URL de autorização
                                                        .tokenUrl(authorizationTokenUri) // URL de obtenção do token
                                                        .scopes(new Scopes() // Definição dos escopos
                                                                .addString("openid", "OpenID connect")
                                                                .addString("profile", "User profile")
                                                        )
                                                )
                                        ))
                )
                // Informação básica sobre a API
                .info(new Info()
                        .title("API REST") // Título da API
                        .description("API REST") // Descrição da API
                        .version("1.0.0") // Versão da API
                );
    }
}//class
