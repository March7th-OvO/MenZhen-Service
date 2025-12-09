package com.oms.menzhenservice.service;

import com.oms.menzhenservice.dto.RegisterDTO;
import com.oms.menzhenservice.entity.Doctor;
import com.oms.menzhenservice.entity.User;
import com.oms.menzhenservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DoctorService doctorService;

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功返回true，失败返回false
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(registerDTO.getUsername());
        if (existingUser != null) {
            return false; // 用户名已存在
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setRealName(registerDTO.getRealName());
        user.setRole(registerDTO.getRole());
        user.setStatus(1);
        user.setCreateTime(java.time.LocalDateTime.now());

        // 插入新用户
        int result = userMapper.insert(user);

        // 如果用户角色是医生，则同时在医生表中创建记录
        if (result > 0 && "doctor".equals(registerDTO.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setName(user.getRealName());
            doctor.setDeptId(registerDTO.getDeptId());
            doctor.setTitle(registerDTO.getTitle());
            doctor.setPhone(registerDTO.getPhone());
            doctor.setSchedulingStatus(0); // 默认休息
            doctor.setCreateTime(java.time.LocalDateTime.now());
            doctorService.addDoctor(doctor);
        }

        return result > 0;
    }

    // 保留原有的 register 方法供兼容
    @Transactional
    public boolean register(User user) {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRealName(user.getRealName());
        dto.setRole(user.getRole());
        return register(dto);
    }
}