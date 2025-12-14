package com.oms.menzhenservice.service;

import com.oms.menzhenservice.entity.RegistrationLevel;
import com.oms.menzhenservice.mapper.RegistrationLevelMapper;
import com.oms.menzhenservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationLevelService {

    @Autowired
    private RegistrationLevelMapper levelMapper;

    @Autowired
    private RedisUtil redisUtil;

    private static final String REGISTRATION_LEVELS_CACHE_KEY = "registration_levels:list";

    public List<RegistrationLevel> getAllLevels() {
        // 先尝试从Redis缓存中获取
        List<RegistrationLevel> levels = (List<RegistrationLevel>) redisUtil.get(REGISTRATION_LEVELS_CACHE_KEY);
        if (levels != null) {
            return levels;
        }

        // 如果缓存中没有，则从数据库查询
        levels = levelMapper.findAll();
        
        // 将结果存入Redis缓存，设置过期时间为30分钟
        redisUtil.set(REGISTRATION_LEVELS_CACHE_KEY, levels, 1800);
        
        return levels;
    }

    @Transactional
    public void saveOrUpdate(RegistrationLevel level) {
        if (level.getLevelId() == null) {
            levelMapper.insert(level);
        } else {
            levelMapper.update(level);
        }
        
        // 清除挂号级别列表缓存
        redisUtil.delete(REGISTRATION_LEVELS_CACHE_KEY);
    }
}