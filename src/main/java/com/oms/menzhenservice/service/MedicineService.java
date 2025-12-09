package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.Medicine;
import com.oms.menzhenservice.mapper.MedicineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    public List<Medicine> getAllActiveMedicines() {
        return medicineMapper.findAllActive();
    }

    public List<Medicine> searchMedicines(String keyword) {
        if (keyword == null) keyword = "";
        return medicineMapper.search(keyword);
    }

    public void saveOrUpdate(Medicine medicine) {
        if (medicine.getMedId() == null) {
            medicineMapper.insert(medicine);
        } else {
            medicineMapper.update(medicine);
        }
    }

    public Medicine getById(Long id) {
        return medicineMapper.findById(id);
    }
}