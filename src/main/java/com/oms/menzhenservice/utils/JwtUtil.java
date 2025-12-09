package com.oms.menzhenservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; // 新增
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct; // Spring Boot 3 使用 jakarta
// 如果是 Spring Boot 2，请使用 import javax.annotation.PostConstruct;

import java.nio.charset.StandardCharsets; // 新增
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. 注入配置文件中的密钥字符串
    @Value("${jwt.secret}")
    private String secret;

    // 2. 定义实例变量（去掉 static final）
    private Key key;

    private static final long EXPIRATION = 86400000L; // 24小时

    // 3. 初始化 Key (在构造函数执行后自动调用)
    @PostConstruct
    public void init() {
        // 使用固定的字符串生成 Key，解决重启失效问题
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256) // 这里改用实例变量 key，并指定算法
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 这里改用实例变量 key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}