package com.oms.menzhenservice.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RegistrationLevel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long levelId;
    private String levelName;
    private BigDecimal fee;
    private Integer isDefault; // 1:是 0:否
    private Integer status;    // 1:正常 0:禁用
}