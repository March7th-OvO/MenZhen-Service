package com.oms.menzhenservice.service;

import com.oms.menzhenservice.dto.RegistrationDTO;
import com.oms.menzhenservice.entity.Registration;
import com.oms.menzhenservice.entity.RegistrationLevel;
import com.oms.menzhenservice.mapper.RegistrationLevelMapper;
import com.oms.menzhenservice.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private RegistrationLevelMapper levelMapper; // 需要查询费用

    @Transactional
    public Registration createRegistration(RegistrationDTO dto) {
        // 1. 获取挂号费
        // 这里不查 Redis，直接查库或从 Service 调用均可。为保持简单直接查库。
        // 如果想利用缓存，可以注入 RegistrationLevelService
        // RegistrationLevel level = levelService.getById(dto.getLevelId());

        // 假设这里直接用 Mapper 简单查（实际建议走 Service 拿缓存）
        RegistrationLevel level = levelMapper.findAll().stream()
                .filter(l -> l.getLevelId().equals(dto.getLevelId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("挂号级别不存在"));

        // 2. 构建挂号单
        Registration reg = new Registration();
        reg.setPatientId(dto.getPatientId());
        reg.setDoctorId(dto.getDoctorId());
        reg.setDeptId(dto.getDeptId());
        reg.setLevelId(dto.getLevelId());
        reg.setSettlementId(dto.getSettlementId());
        reg.setRegDate(dto.getRegDate());
        reg.setRegTimeSlot(dto.getRegTimeSlot());
        reg.setFee(level.getFee()); // 设置费用

        // 3. 保存
        registrationMapper.insert(reg);
        return reg;
    }

    public void cancel(Long regId) {
        registrationMapper.cancelRegistration(regId);
    }
    
    // 查询所有挂号单
    public List<Registration> findAllRegistrations() {
        return registrationMapper.findAll();
    }
    
    // 根据患者ID查询挂号记录
    public List<Registration> findRegistrationsByPatientId(Long patientId) {
        return registrationMapper.findByPatientId(patientId);
    }
    
    // 根据医生ID查询挂号记录
    public List<Registration> findRegistrationsByDoctorId(Long doctorId) {
        return registrationMapper.findByDoctorId(doctorId);
    }
}