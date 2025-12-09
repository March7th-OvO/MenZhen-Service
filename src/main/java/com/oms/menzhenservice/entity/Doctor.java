package com.oms.menzhenservice.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Doctor {
    private Long doctorId;
    private String name;
    private Long deptId;
    private String title;
    private String phone;
    private Integer schedulingStatus; // 1:出诊 0:休息
    private LocalDateTime createTime;
    private Long userId;

    private String deptName;
}