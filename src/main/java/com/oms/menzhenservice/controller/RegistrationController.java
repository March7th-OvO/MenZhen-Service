package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.dto.RegistrationDTO;
import com.oms.menzhenservice.entity.Registration;
import com.oms.menzhenservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

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
}