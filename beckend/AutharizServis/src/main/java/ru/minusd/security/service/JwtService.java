package ru.minusd.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.PersonalDTO;
import ru.minusd.security.domain.dto.ValidToken;
import ru.minusd.security.domain.model.Personal;
import ru.minusd.security.domain.model.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${token.signing.keyAccess}")
    private String jwtSigningKeyAccess;

    @Value("${token.signing.keyRefresh}")
    private String jwtSigningKeyRefresh;

    private final UserService userService;


    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерация токена
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("role", customUserDetails.getRole());
        }
        return generateAccessToken(claims, userDetails);
    }

    /**
     * Генерация токена для сервисов
     *
     * @param personal данные пользователя
     * @return токен
     */
    public String generateTokenService(PersonalDTO personal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", personal.getId());
        claims.put("privileges", personal.getPrivileges());

        return generateJwtTokenService(claims, personal);
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация Access токена
     *
     * @param extraClaims дополнительные данные
     * @param userDetails данные пользователя
     * @return токен
     */
    private String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(jwtSigningKeyAccess))
                .compact();
    }

    /**
     * Генерация JwtTokenService токена
     *
     * @param extraClaims дополнительные данные
     * @param personal данные пользователя
     * @return токен
     */
    private String generateJwtTokenService(Map<String, Object> extraClaims,PersonalDTO personal ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(personal.getPersonal_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(jwtSigningKeyAccess))
                .compact();
    }

    /**
     * Генерация Refresh токена
     *
     * @param user имя пользователя
     * @return токен
     */
    public String generateRefreshToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(jwtSigningKeyRefresh))
                .compact();
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey(jwtSigningKeyAccess)).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey(String jwtSigningKey) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
