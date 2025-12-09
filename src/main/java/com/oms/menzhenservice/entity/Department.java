package com.oms.menzhenservice.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long deptId;
    private String deptName;
    private String deptCode;
    private String category; // 内科、外科等
    private Integer status;  // 1启用 0禁用
}
