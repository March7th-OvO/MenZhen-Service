package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("SELECT * FROM department WHERE status = 1")
    List<Department> findAll();

    @Select("SELECT * FROM department WHERE dept_id = #{deptId}")
    Department findById(Long deptId);

    @Insert("INSERT INTO department(dept_name, dept_code, category, status) VALUES(#{deptName}, #{deptCode}, #{category}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "deptId")
    int insert(Department department);

    @Update("UPDATE department SET dept_name=#{deptName}, category=#{category} WHERE dept_id=#{deptId}")
    int update(Department department);

    // 逻辑删除
    @Update("UPDATE department SET status = 0 WHERE dept_id=#{deptId}")
    int delete(Long deptId);
}