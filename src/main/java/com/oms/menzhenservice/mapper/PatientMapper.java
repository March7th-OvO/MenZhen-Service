package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.Patient;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PatientMapper {
    @Select("SELECT * FROM patient WHERE id_card = #{idCard}")
    Patient findByIdCard(String idCard);

    @Insert("INSERT INTO patient(name, id_card, gender, age, phone, address, birth_date) " +
            "VALUES(#{name}, #{idCard}, #{gender}, #{age}, #{phone}, #{address}, #{birthDate})")
    @Options(useGeneratedKeys = true, keyProperty = "patientId")
    int insert(Patient patient);

    @Update("UPDATE patient SET phone=#{phone}, address=#{address} WHERE patient_id=#{patientId}")
    int update(Patient patient);
    
    // 根据患者ID查询患者信息
    @Select("SELECT * FROM patient WHERE patient_id = #{patientId}")
    Patient findById(Long patientId);
}