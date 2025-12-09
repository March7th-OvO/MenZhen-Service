package com.oms.menzhenservice.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SettlementCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long categoryId;
    private String categoryName;
    private BigDecimal ratio; // 报销比例
    private Integer status;
}