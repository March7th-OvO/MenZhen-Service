package com.oms.menzhenservice.controller;

import com.oms.menzhenservice.common.Result;
import com.oms.menzhenservice.entity.Department;
import com.oms.menzhenservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public Result<List<Department>> list() {
        return Result.success(departmentService.getAllDepartments());
    }

    @GetMapping("/{deptId}")
    public Result<Department> getDepartmentById(@PathVariable Long deptId) {
        Department department = departmentService.getDepartmentById(deptId);
        if (department != null) {
            return Result.success(department);
        } else {
            return Result.error(400, "科室不存在");
        }
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody Department department) {
        departmentService.updateDepartment(department);
        return Result.success(); // 以前返回 "Success"，现在返回标准结构
    }
}
