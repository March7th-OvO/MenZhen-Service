package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.RegistrationLevel;
import com.oms.menzhenservice.mapper.RegistrationLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationLevelService {

    @Autowired
    private RegistrationLevelMapper levelMapper;

    public List<RegistrationLevel> getAllLevels() {
        return levelMapper.findAll();
    }

    @Transactional
    public void saveOrUpdate(RegistrationLevel level) {
        if (level.getLevelId() == null) {
            levelMapper.insert(level);
        } else {
            levelMapper.update(level);
        }
    }
}