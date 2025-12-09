package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.RegistrationLevel;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RegistrationLevelMapper {
    @Select("SELECT * FROM registration_level WHERE status = 1")
    List<RegistrationLevel> findAll();

    @Insert("INSERT INTO registration_level(level_name, fee, is_default, status) VALUES(#{levelName}, #{fee}, #{isDefault}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "levelId")
    int insert(RegistrationLevel level);

    @Update("UPDATE registration_level SET level_name=#{levelName}, fee=#{fee}, is_default=#{isDefault} WHERE level_id=#{levelId}")
    int update(RegistrationLevel level);
}