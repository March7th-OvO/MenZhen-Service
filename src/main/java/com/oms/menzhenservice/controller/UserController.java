
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

    @PostMapping("/reset-password")
    public Result<String> resetPassword(@RequestBody Map<String, String> resetInfo) {
        String username = resetInfo.get("username");
        String idNumber = resetInfo.get("idCard");  // 修改为 idCard 以匹配前端字段
        String phone = resetInfo.get("phone");
        String newPassword = resetInfo.get("newPassword");  // 修改为 newPassword 以匹配前端字段

        // 参数校验
        if (username == null || username.isEmpty() ||
            idNumber == null || idNumber.isEmpty() ||
            phone == null || phone.isEmpty() ||
            newPassword == null || newPassword.isEmpty()) {
            return Result.error(400, "所有字段都是必填的");
        }

        // 调用服务层重置密码
        boolean success = userService.resetPassword(username, idNumber, phone, newPassword);
        if (success) {
            return Result.success("密码重置成功");
        } else {
            return Result.error(400, "用户信息验证失败，无法重置密码");
        }
    }

    @PostMapping("/verify-user-info")
    public Result<Map<String, Object>> verifyUserInfo(@RequestBody Map<String, String> userInfo) {
        String username = userInfo.get("username");
        String idNumber = userInfo.get("id_number");
        String phone = userInfo.get("phone");

        // 验证用户信息逻辑
        User user = userMapper.findByUsername(username);
        Map<String, Object> result = new HashMap<>();

        if (user != null) {
            boolean isValid = true;
            String message = "验证成功";

            if (!user.getIdNumber().equals(idNumber)) {
                isValid = false;
                message = "身份证号码不匹配";
            } else if (!user.getPhone().equals(phone)) {
                isValid = false;
                message = "手机号码不匹配";
            }

            result.put("valid", isValid);
            result.put("message", message);
            result.put("user", user);

            if (isValid) {
                return Result.success(result);
            } else {
                return Result.error(400, message);
            }
        } else {
            result.put("valid", false);
            result.put("message", "用户不存在");
            return Result.error(400, "用户不存在");
        }
    }

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