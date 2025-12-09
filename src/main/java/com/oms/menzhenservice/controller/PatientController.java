package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.Patient;
import com.oms.menzhenservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    // 挂号时输入身份证回车查询
    @GetMapping("/info/{idCard}")
    public Result<Patient> getInfo(@PathVariable String idCard) {
        return Result.success(patientService.getPatientByIdCard(idCard));
    }

    // 新患者建档
    @PostMapping("/add")
    public Result<Patient> add(@RequestBody Patient patient) {
        return Result.success(patientService.createPatient(patient));
    }
}