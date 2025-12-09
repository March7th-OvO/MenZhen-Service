package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.SettlementCategory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SettlementCategoryMapper {
    @Select("SELECT * FROM settlement_category WHERE status = 1")
    List<SettlementCategory> findAll();

    @Insert("INSERT INTO settlement_category(category_name, ratio, status) VALUES(#{categoryName}, #{ratio}, #{status})")
    int insert(SettlementCategory category);
}