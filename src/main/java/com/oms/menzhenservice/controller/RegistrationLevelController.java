package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.RegistrationLevel;
import com.oms.menzhenservice.service.RegistrationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reg-level")
public class RegistrationLevelController {
    @Autowired
    private RegistrationLevelService service;

    @GetMapping("/list")
    public Result<List<RegistrationLevel>> list() {
        return Result.success(service.getAllLevels());
    }

    @PostMapping("/save")
    public Result<Void> save(@RequestBody RegistrationLevel level) {
        service.saveOrUpdate(level);
        return Result.success();
    }
}