package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.Doctor;
import com.oms.menzhenservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
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

    // 获取当前登录用户的医生信息
    @GetMapping("/info")
    public Result<Doctor> getCurrentDoctorInfo(HttpServletRequest request) {
        // 从请求中获取当前用户ID
        Object userIdObj = request.getAttribute("currId");
        if (userIdObj == null) {
            return Result.error(400, "用户未登录");
        }

        // 正确处理Integer到Long的转换
        Long userId;
        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        } else {
            return Result.error(400, "用户ID类型错误");
        }

        Doctor doctor = doctorService.getDoctorByUserId(userId);
        if (doctor == null) {
            return Result.error(400, "当前用户不是医生");
        }

        return Result.success(doctor);
    }
}
