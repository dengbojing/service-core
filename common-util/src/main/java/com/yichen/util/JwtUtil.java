package com.yichen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yichen.util.POJO.Subject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.timeout}")
    private Long timeout;

    private ObjectMapper objectMapper;

    public JwtUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 生成 JWT Token
     *
     * @param subject 数据
     * @return token
     */
    public String general(Subject subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = key();
        String message = "";
        try {
            message = objectMapper.writeValueAsString(subject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JwtBuilder builder = Jwts.builder()
                .setId(clientId)
                .setIssuedAt(now)
                .setSubject(message)
                .signWith(key, signatureAlgorithm);
        Date exp = new Date(nowMillis + timeout * 1000);
        builder.setExpiration(exp);
        return builder.compact();
    }

    /**
     * 解析Token
     *
     * @param jwt token
     * @return 解析结果
     */
    public Claims parse(String jwt) {
        return Jwts.parser().setSigningKey(key()).parseClaimsJws(jwt).getBody();
    }

    /**
     * 解析Token为subject
     *
     * @param jwt token
     * @return 解析结果
     */
    public Subject subject(String jwt) {
        String subject = Jwts.parser().setSigningKey(key()).parseClaimsJws(jwt).getBody().getSubject();
        try {
            return objectMapper.readValue(subject, Subject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Base64Utils.encode(key.getBytes()));
    }
}
