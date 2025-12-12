package com.oms.menzhenservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistrationDTO {
    private Long patientId;
    private Long doctorId;
    private Long deptId;
    private Long levelId;      // 挂号级别ID (决定费用)
    private Long settlementId; // 结算类别ID
    private LocalDate regDate;
    private String regTimeSlot;
}