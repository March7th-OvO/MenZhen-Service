package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.Doctor;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DoctorMapper {
    // 联表查询科室名称
    @Select("SELECT d.*, dept.dept_name as deptName FROM doctor d LEFT JOIN department dept ON d.dept_id = dept.dept_id")
    List<Doctor> findAll();

    @Select("SELECT * FROM doctor WHERE dept_id = #{deptId} AND scheduling_status = 1")
    List<Doctor> findAvailableByDept(Long deptId);

    @Insert("INSERT INTO doctor(name, dept_id, title, phone, scheduling_status) VALUES(#{name}, #{deptId}, #{title}, #{phone}, 1)")
    int insert(Doctor doctor);
}