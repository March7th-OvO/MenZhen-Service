package com.oms.menzhenservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    // 1. 注入配置文件中的密钥字符串
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256) // 这里改用实例变量 key，并指定算法
                .compact();
        
        // 将token存储到Redis中，设置过期时间与JWT一致
        redisTemplate.opsForValue().set("token:" + username, token, EXPIRATION, TimeUnit.MILLISECONDS);
        
        return token;
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 这里改用实例变量 key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public void invalidateToken(String username) {
        redisTemplate.delete("token:" + username);
    }
    
    public boolean isTokenValid(String username, String token) {
        String cachedToken = (String) redisTemplate.opsForValue().get("token:" + username);
        return token.equals(cachedToken);
    }
}