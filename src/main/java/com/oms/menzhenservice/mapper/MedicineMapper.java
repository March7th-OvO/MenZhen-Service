package com.oms.menzhenservice.mapper;

import com.oms.menzhenservice.entity.Medicine;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MedicineMapper {

    // 查询所有在售药品（供医生下拉选择）
    @Select("SELECT * FROM medicine WHERE status = 1")
    List<Medicine> findAllActive();

    // 管理员查询列表（可支持模糊搜索）
    @Select("SELECT * FROM medicine WHERE med_name LIKE CONCAT('%',#{keyword},'%')")
    List<Medicine> search(String keyword);

    @Select("SELECT * FROM medicine WHERE med_id = #{medId}")
    Medicine findById(Long medId);

    @Insert("INSERT INTO medicine(med_code, med_name, format, price, stock, category, status) " +
            "VALUES(#{medCode}, #{medName}, #{format}, #{price}, #{stock}, #{category}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "medId")
    int insert(Medicine medicine);

    @Update("UPDATE medicine SET med_code=#{medCode}, med_name=#{medName}, format=#{format}, price=#{price}, stock=#{stock}, category=#{category}, status=#{status} WHERE med_id=#{medId}")
    int update(Medicine medicine);

    /**
     * 核心：扣减库存
     * 这里的 stock >= #{quantity} 是关键，防止库存扣成负数
     * 返回影响行数：1表示成功，0表示库存不足
     */
    @Update("UPDATE medicine SET stock = stock - #{quantity} WHERE med_id = #{medId} AND stock >= #{quantity}")
    int deductStock(Long medId, Integer quantity);
}