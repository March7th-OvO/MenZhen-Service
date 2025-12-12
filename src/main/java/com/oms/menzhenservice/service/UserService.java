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
     * 重置用户密码
     * @param username 用户名
     * @param idNumber 身份证号码
     * @param phone 手机号
     * @param newPassword 新密码
     * @return 重置成功返回true，失败返回false
     */
    public boolean resetPassword(String username, String idNumber, String phone, String newPassword) {
        // 查找用户
        User user = userMapper.findByUsername(username);
        
        // 用户不存在
        if (user == null) {
            return false;
        }
        
        // 验证身份证号码和手机号
        if (!user.getIdNumber().equals(idNumber) || !user.getPhone().equals(phone)) {
            return false;
        }
        
        // 更新密码
        int result = userMapper.updatePasswordByUsername(username, newPassword);
        return result > 0;
    }

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
        user.setIdNumber(registerDTO.getIdNumber());
        user.setPhone(registerDTO.getPhone());
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
            doctor.setSchedulingStatus(1); // 默认出诊
            doctor.setCreateTime(java.time.LocalDateTime.now());
            doctor.setUserId(user.getUserId()); // 关联userId
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
        dto.setIdNumber(user.getIdNumber());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return register(dto);
    }
}