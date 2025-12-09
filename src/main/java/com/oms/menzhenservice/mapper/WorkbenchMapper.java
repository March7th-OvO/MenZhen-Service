package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.MedicalRecord;
import com.oms.menzhenservice.entity.Registration;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface WorkbenchMapper {

    // 1. 医生查看待诊患者 (状态为1:已挂号)
    @Select("SELECT r.*, p.name as patientName, p.gender, p.age " +
            "FROM registration r " +
            "LEFT JOIN patient p ON r.patient_id = p.patient_id " +
            "WHERE r.doctor_id = #{doctorId} AND r.status = 1")
    List<Map<String, Object>> findPendingPatients(Long doctorId);

    // 2. 插入病历
    @Insert("INSERT INTO medical_record(reg_id, patient_id, doctor_id, description, diagnosis, advice) " +
            "VALUES(#{regId}, #{patientId}, #{doctorId}, #{description}, #{diagnosis}, #{advice})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    int insertRecord(MedicalRecord record);

    // 3. 插入处方主表
    @Insert("INSERT INTO prescription(record_id, reg_id, total_amount, status) " +
            "VALUES(#{recordId}, #{regId}, #{totalAmount}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "presId")
    int insertPrescription(Map<String, Object> prescriptionMap);

    // 4. 插入处方明细
    @Insert("INSERT INTO prescription_item(pres_id, med_id, med_name, price, quantity, usage_info) " +
            "VALUES(#{presId}, #{medId}, #{medName}, #{price}, #{quantity}, #{usageInfo})")
    int insertPrescriptionItem(Map<String, Object> itemMap);

    // 5. 更新挂号状态为已就诊 (状态2)
    @Update("UPDATE registration SET status = 2 WHERE reg_id = #{regId}")
    int updateRegStatusToComplete(Long regId);
}