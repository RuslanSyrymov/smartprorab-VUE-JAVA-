package ru.gatewey.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
//Проверяем, если в PATH есть указанные ниже пути, то
    public static final List<String> openApiEndpoints = List.of(
            "/auth/sign-up",
            "/auth/sign-in",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

