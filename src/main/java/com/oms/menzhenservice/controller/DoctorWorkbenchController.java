package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.dto.DiagnosisDTO;
import com.oms.menzhenservice.service.DoctorWorkbenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor/workbench")
public class DoctorWorkbenchController {

    @Autowired
    private DoctorWorkbenchService workbenchService;

    // 获取当前医生的待诊患者列表
    // URL: /api/doctor/workbench/pending?doctorId=1
    @GetMapping("/pending")
    public Result<List<Map<String, Object>>> getPendingList(@RequestParam Long doctorId) {
        return Result.success(workbenchService.getPendingPatients(doctorId));
    }

    @PostMapping("/diagnose")
    public Result<Void> submitDiagnosis(@RequestBody DiagnosisDTO dto) {
        workbenchService.submitDiagnosis(dto);
        return Result.success();
    }
}