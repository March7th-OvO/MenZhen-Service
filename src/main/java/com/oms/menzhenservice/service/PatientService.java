package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.Patient;
import com.oms.menzhenservice.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientMapper patientMapper;

    /**
     * 根据身份证查询，如果没有则需前端引导去建档
     */
    public Patient getPatientByIdCard(String idCard) {
        return patientMapper.findByIdCard(idCard);
    }

    public Patient createPatient(Patient patient) {
        // 简单校验是否存在
        Patient exist = patientMapper.findByIdCard(patient.getIdCard());
        if (exist != null) {
            throw new RuntimeException("患者已存在");
        }
        patientMapper.insert(patient);
        return patient;
    }
}