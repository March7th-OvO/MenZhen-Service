package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.Medicine;
import com.oms.menzhenservice.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // 医生开方时：获取所有可用药品列表
    @GetMapping("/list-active")
    public Result<List<Medicine>> listActive() {
        return Result.success(medicineService.getAllActiveMedicines());
    }

    // 药房管理员：搜索药品
    @GetMapping("/search")
    public Result<List<Medicine>> search(@RequestParam(required = false) String keyword) {
        return Result.success(medicineService.searchMedicines(keyword));
    }

    // 药房管理员：新增或修改药品
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Medicine medicine) {
        medicineService.saveOrUpdate(medicine);
        return Result.success();
    }
}