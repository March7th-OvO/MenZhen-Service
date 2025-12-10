package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.Department;
import com.oms.menzhenservice.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有科室
     */
    public List<Department> getAllDepartments() {
        return departmentMapper.findAll();
    }

    /**
     * 根据ID查询科室
     */
    public Department getDepartmentById(Long deptId) {
        return departmentMapper.findById(deptId);
    }

    /**
     * 更新科室信息
     */
    @Transactional
    public void updateDepartment(Department department) {
        departmentMapper.update(department);
    }

    /**
     * 新增科室
     */
    @Transactional
    public void addDepartment(Department department) {
        departmentMapper.insert(department);
    }
}
