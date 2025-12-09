
package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.dto.RegisterDTO;
import com.oms.menzhenservice.entity.User;
import com.oms.menzhenservice.mapper.UserMapper;
import com.oms.menzhenservice.service.UserService;
import com.oms.menzhenservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User loginUser) {
        User user = userMapper.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("role", user.getRole());
            data.put("name", user.getRealName());
            return Result.success(data);
        } else {
            return Result.error(400, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        // 基本参数验证
        if (registerDTO.getUsername() == null || registerDTO.getUsername().isEmpty() ||
                registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty() ||
                registerDTO.getRealName() == null || registerDTO.getRealName().isEmpty()) {
            return Result.error(400, "用户名、密码和真实姓名不能为空");
        }

        // 如果是医生角色，验证医生相关字段
        if ("doctor".equals(registerDTO.getRole())) {
            if (registerDTO.getDeptId() == null ||
                    registerDTO.getTitle() == null || registerDTO.getTitle().isEmpty() ||
                    registerDTO.getPhone() == null || registerDTO.getPhone().isEmpty()) {
                return Result.error(400, "医生角色需要填写科室、职称和电话");
            }
        }

        boolean success = userService.register(registerDTO);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error(400, "用户名已存在");
        }
    }
}