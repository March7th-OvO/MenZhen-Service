package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.Department;
import com.oms.menzhenservice.mapper.DepartmentMapper;
import com.oms.menzhenservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RedisUtil redisUtil;

    private static final String DEPARTMENT_CACHE_KEY = "departments:list";
    private static final String DEPARTMENT_ID_CACHE_KEY_PREFIX = "department:id:";

    /**
     * 查询所有科室
     */
    public List<Department> getAllDepartments() {
        // 先尝试从Redis缓存中获取
        List<Department> departments = (List<Department>) redisUtil.get(DEPARTMENT_CACHE_KEY);
        if (departments != null) {
            return departments;
        }

        // 如果缓存中没有，则从数据库查询
        departments = departmentMapper.findAll();
        
        // 将结果存入Redis缓存，设置过期时间为30分钟
        redisUtil.set(DEPARTMENT_CACHE_KEY, departments, 1800);
        
        return departments;
    }

    /**
     * 根据ID查询科室
     */
    public Department getDepartmentById(Long deptId) {
        // 构造缓存键
        String cacheKey = DEPARTMENT_ID_CACHE_KEY_PREFIX + deptId;
        
        // 先尝试从Redis缓存中获取
        Department department = (Department) redisUtil.get(cacheKey);
        if (department != null) {
            return department;
        }

        // 如果缓存中没有，则从数据库查询
        department = departmentMapper.findById(deptId);
        
        // 将结果存入Redis缓存，设置过期时间为30分钟
        if (department != null) {
            redisUtil.set(cacheKey, department, 1800);
        }
        
        return department;
    }

    /**
     * 更新科室信息
     */
    @Transactional
    public void updateDepartment(Department department) {
        departmentMapper.update(department);
        
        // 清除相关缓存
        redisUtil.delete(DEPARTMENT_ID_CACHE_KEY_PREFIX + department.getDeptId());
        redisUtil.delete(DEPARTMENT_CACHE_KEY);
    }

    /**
     * 新增科室
     */
    @Transactional
    public void addDepartment(Department department) {
        departmentMapper.insert(department);
        
        // 清除科室列表缓存
        redisUtil.delete(DEPARTMENT_CACHE_KEY);
    }
}
