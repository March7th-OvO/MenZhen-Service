package com.oms.menzhenservice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Registration {
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private Long deptId;
    private Long levelId;
    private Long settlementId;
    private LocalDate regDate;
    private String regTimeSlot; // 上午/下午
    private BigDecimal fee;
    private Integer status; // 1:已挂号
    private Long userId;    // 操作员
    private LocalDateTime createTime;
}