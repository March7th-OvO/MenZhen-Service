package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.Doctor;
import com.oms.menzhenservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    // 挂号时选择科室后，联动加载该科室的医生
    @GetMapping("/list-available")
    public Result<List<Doctor>> listAvailable(@RequestParam Long deptId) {
        return Result.success(doctorService.getAvailableDoctors(deptId));
    }

    // 管理员：获取所有医生
    @GetMapping("/list")
    public Result<List<Doctor>> listAll() {
        return Result.success(doctorService.getAllDoctors());
    }

    // 管理员：新增医生
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        return Result.success();
    }
}