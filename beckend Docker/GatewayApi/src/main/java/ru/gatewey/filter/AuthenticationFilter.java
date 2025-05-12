package ru.gatewey.filter;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.gatewey.DTO.JwtAuthenticationResponse;
import ru.gatewey.util.JwtUtil;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${url.authValid}")
    private String urlValid;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //заголовок содержит токен или нет
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    template.postForEntity("http://SECURITY-SECURITY//auth/sign-in", exchange.getRequest(), String.class);
//                    System.out.println("YTYTYTYTYT");
                    throw new RuntimeException("Отсутствует заголовок авторизации");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                ResponseEntity<JwtAuthenticationResponse> responseEntity = template.getForEntity(urlValid + authHeader, JwtAuthenticationResponse.class);
                JwtAuthenticationResponse jwtAuthenticationResponse = responseEntity.getBody();
                System.out.println(jwtAuthenticationResponse);

                try {
                    if (jwtAuthenticationResponse.getJwtTokenService() != null) {
                        var strJwt = jwtAuthenticationResponse.getJwtTokenService();
                        var strAccess = jwtAuthenticationResponse.getAccessToken();
                        ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                .headers(h -> h.setBearerAuth(strJwt))
                                .build();
                        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return chain.filter(mutatedExchange)
                                .then(Mono.just(exchange))
                                .map(serverWebExchange -> {
                                    serverWebExchange.getResponse().getHeaders().set("Authorization", "Bearer " + strAccess);
                                    return serverWebExchange;
                                })
                                .then();
                    }

                } catch (Exception e) {

                    System.out.println("Недействительный доступ...!");
                    throw new RuntimeException("Несанкционированный доступ к приложению");
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}

