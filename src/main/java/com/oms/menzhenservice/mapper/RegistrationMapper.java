package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.Registration;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RegistrationMapper {

    @Insert("INSERT INTO registration(patient_id, doctor_id, dept_id, level_id, settlement_id, reg_date, reg_time_slot, fee, status, user_id) " +
            "VALUES(#{patientId}, #{doctorId}, #{deptId}, #{levelId}, #{settlementId}, #{regDate}, #{regTimeSlot}, #{fee}, 1, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "regId")
    int insert(Registration registration);

    // 查询某医生某天的挂号列表
    @Select("SELECT * FROM registration WHERE doctor_id = #{doctorId} AND reg_date = #{date} AND status = 1")
    List<Registration> findByDoctorAndDate(Long doctorId, String date);

    // 退号
    @Update("UPDATE registration SET status = 3 WHERE reg_id = #{regId}")
    int cancelRegistration(Long regId);
}