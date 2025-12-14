package com.oms.menzhenservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redis-test")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/set")
    public String set() {
        redisTemplate.opsForValue().set("test-key", "Hello Redis!");
        return "设置成功";
    }

    @GetMapping("/get")
    public String get() {
        Object value = redisTemplate.opsForValue().get("test-key");
        return "获取到的值: " + (value != null ? value.toString() : "未找到");
    }
}