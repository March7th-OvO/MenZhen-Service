package com.oms.menzhenservice.service;

import com.oms.menzhenservice.dto.DiagnosisDTO;
import com.oms.menzhenservice.entity.MedicalRecord;
import com.oms.menzhenservice.mapper.MedicineMapper;
import com.oms.menzhenservice.mapper.WorkbenchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorWorkbenchService {

    @Autowired
    private WorkbenchMapper workbenchMapper;
    @Autowired
    private MedicineMapper medicineMapper; // 注入药品 Mapper

    /**
     * 获取医生当前的候诊列表
     */
    public List<Map<String, Object>> getPendingPatients(Long doctorId) {
        return workbenchMapper.findPendingPatients(doctorId);
    }

    /**
     * 提交诊疗结果 (核心：写病历 + 开处方 + 改状态)
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitDiagnosis(DiagnosisDTO dto) {
        // 1. 保存病历
        MedicalRecord record = new MedicalRecord();
        record.setRegId(dto.getRegId());
        record.setPatientId(dto.getPatientId());
        record.setDoctorId(dto.getDoctorId());
        record.setDescription(dto.getDescription());
        record.setDiagnosis(dto.getDiagnosis());
        record.setAdvice(dto.getAdvice());
        workbenchMapper.insertRecord(record);

        // 2. 处理处方 (如果有药)
        if (dto.getMedicines() != null && !dto.getMedicines().isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;

            // --- [新增逻辑] 先计算总价 + 循环保存 ---
            // 为了拿到 presId，我们先插入处方主表(Total暂存为0，或者算完再存，这里演示算完再存)
            for (DiagnosisDTO.PrescriptionItemDTO item : dto.getMedicines()) {
                BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
                total = total.add(itemTotal);
            }

            Map<String, Object> presMap = new HashMap<>();
            presMap.put("recordId", record.getRecordId());
            presMap.put("regId", dto.getRegId());
            presMap.put("totalAmount", total);
            workbenchMapper.insertPrescription(presMap);
            // 修改这一行: 兼容 BigInteger/Long 等数字类型
            Long presId = ((Number) presMap.get("presId")).longValue();

            // --- [核心修改] 循环插入明细 并 扣减库存 ---
            for (DiagnosisDTO.PrescriptionItemDTO item : dto.getMedicines()) {
                // A. 扣减库存
                int rows = medicineMapper.deductStock(item.getMedId(), item.getQuantity());
                if (rows == 0) {
                    // 如果返回0，说明库存不足 (SQL中的 stock >= quantity 条件不满足)
                    // 抛出异常，触发 @Transactional 回滚，之前的病历和处方都不会保存
                    throw new RuntimeException("药品库存不足：" + item.getMedName());
                }

                // B. 插入处方明细
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("presId", presId);
                itemMap.put("medId", item.getMedId());
                itemMap.put("medName", item.getMedName());
                itemMap.put("price", item.getPrice());
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("usageInfo", item.getUsageInfo());
                workbenchMapper.insertPrescriptionItem(itemMap);
            }
        }

        // 3. 修改挂号单状态为 "2:已就诊"
        workbenchMapper.updateRegStatusToComplete(dto.getRegId());
    }
}