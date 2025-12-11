package com.oms.menzhenservice.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    // 用户基本信息
    private String username;
    private String password;
    private String realName;
    private String idNumber;
    private String phone;
    private String role;

    // 医生扩展信息（仅当 role=doctor 时需要）
    private Long deptId;
    private String title;
}