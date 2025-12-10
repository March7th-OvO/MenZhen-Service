package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.Doctor;
import com.oms.menzhenservice.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    public List<Doctor> getAllDoctors() {
        return doctorMapper.findAll();
    }

    public List<Doctor> getAvailableDoctors(Long deptId) {
        return doctorMapper.findAvailableByDept(deptId);
    }

    public void addDoctor(Doctor doctor) {
        doctorMapper.insert(doctor);
    }

    // 根据用户ID获取医生信息
    public Doctor getDoctorByUserId(Long userId) {
        return doctorMapper.findByUserId(userId);
    }
}