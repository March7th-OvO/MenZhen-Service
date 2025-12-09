package com.oms.menzhenservice.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DiagnosisDTO {
    // 病历信息
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private String description;
    private String diagnosis;
    private String advice;

    // 处方信息 (可以为空，如果不需开药)
    private List<PrescriptionItemDTO> medicines;

    @Data
    public static class PrescriptionItemDTO {
        private Long medId;
        private String medName;
        private BigDecimal price;
        private Integer quantity;
        private String usageInfo;
    }
}