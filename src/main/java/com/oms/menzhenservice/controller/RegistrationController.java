package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.dto.PatientDoctorNamesDTO;
import com.oms.menzhenservice.dto.RegistrationDTO;
import com.oms.menzhenservice.entity.Registration;
import com.oms.menzhenservice.service.DoctorService;
import com.oms.menzhenservice.service.PatientService;
import com.oms.menzhenservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    public Result<Registration> create(@RequestBody RegistrationDTO dto) {
        // 返回创建成功的挂号单信息
        return Result.success(registrationService.createRegistration(dto));
    }

    @PostMapping("/cancel/{regId}")
    public Result<Void> cancel(@PathVariable Long regId) {
        registrationService.cancel(regId);
        return Result.success();
    }
    
    // 查询所有挂号单
    @GetMapping("/list")
    public Result<List<Registration>> listAllRegistrations() {
        return Result.success(registrationService.findAllRegistrations());
    }
    
    // 根据患者ID查询挂号记录
    @GetMapping("/patient/{patientId}")
    public Result<List<Registration>> getRegistrationsByPatientId(@PathVariable Long patientId) {
        return Result.success(registrationService.findRegistrationsByPatientId(patientId));
    }
    
    // 根据医生ID查询挂号记录
    @GetMapping("/doctor/{doctorId}")
    public Result<List<Registration>> getRegistrationsByDoctorId(@PathVariable Long doctorId) {
        return Result.success(registrationService.findRegistrationsByDoctorId(doctorId));
    }
    
    // 根据患者ID和医生ID查询患者姓名和医生姓名
    @GetMapping("/names")
    public Result<PatientDoctorNamesDTO> getPatientAndDoctorNames(
            @RequestParam Long patientId,
            @RequestParam Long doctorId) {
        PatientDoctorNamesDTO namesDTO = new PatientDoctorNamesDTO();
        
        // 获取患者姓名
        var patient = patientService.getPatientById(patientId);
        if (patient != null) {
            namesDTO.setPatientName(patient.getName());
        }
        
        // 获取医生姓名
        var doctor = doctorService.getDoctorById(doctorId);
        if (doctor != null) {
            namesDTO.setDoctorName(doctor.getName());
        }
        
        return Result.success(namesDTO);
    }
}