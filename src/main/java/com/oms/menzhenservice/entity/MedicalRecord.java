package com.oms.menzhenservice.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedicalRecord {
    private Long recordId;
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private String description; // 主诉
    private String diagnosis;   // 诊断
    private String advice;      // 医嘱
    private LocalDateTime createTime;
}