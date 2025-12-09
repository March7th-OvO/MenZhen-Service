package com.oms.menzhenservice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Medicine {
    private Long medId;
    private String medCode;
    private String medName;
    private String format;      // 规格
    private BigDecimal price;
    private Integer stock;      // 库存
    private String category;
    private Integer status;     // 1:在售 0:停售
    private LocalDateTime createTime;
}