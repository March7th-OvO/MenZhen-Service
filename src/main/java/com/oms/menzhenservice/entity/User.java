package com.oms.menzhenservice.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;  // 用户ID
    private String username;  // 用户名
    private String password; // 密码
    private String realName;  // 真实姓名
    private String idNumber;  // 身份证号码
    private String phone;     // 电话号码
    private String role;     // 权限
    private Integer status;  // 用户状态 0:禁用 1:启用
    private LocalDateTime createTime;  // 用户创建时间
}