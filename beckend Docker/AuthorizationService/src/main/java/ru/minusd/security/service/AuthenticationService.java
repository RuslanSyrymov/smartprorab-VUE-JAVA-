package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.*;
import ru.minusd.security.domain.model.Personal;
import ru.minusd.security.domain.Enum.Role;
import ru.minusd.security.domain.model.Privilege;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.repository.PersonalRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PersonalService personalService;


    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .personal_id(Long.valueOf(request.getPersonal_id()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);
        var jwtAccess = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwtAccess, null);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwtAccess = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwtAccess, null);
    }

    public JwtAuthenticationResponse ValidToken(ValidToken token) {
        var jwt = token.getToken();
        var username = jwtService.extractUserName(jwt);
        String jwtAccess = "";
        String jwtTokenService = "";
        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);
        if (jwtService.isTokenValid(jwt, user) && !jwtService.isTokenExpired(jwt)) {
            var personal = personalService.personalDTOfind(username);
            jwtAccess = jwtService.generateToken(user);
            jwtTokenService = jwtService.generateTokenService(personal);


        }
        return new JwtAuthenticationResponse(jwtAccess, jwtTokenService);
    }





}
