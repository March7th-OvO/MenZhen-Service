package com.oms.menzhenservice.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String username;
    private String password; // 实际项目中请勿明文存储
    private String realName;
    private String role;     // admin, doctor, cashier
    private Integer status;
    private LocalDateTime createTime;
}