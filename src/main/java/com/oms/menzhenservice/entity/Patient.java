package com.oms.menzhenservice.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Patient {
    private Long patientId;
    private String name;
    private String idCard;
    private String gender;
    private Integer age;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private LocalDateTime createTime;
}