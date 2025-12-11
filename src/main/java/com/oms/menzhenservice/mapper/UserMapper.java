package com.oms.menzhenservice.mapper;
import com.oms.menzhenservice.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND password = #{password} AND status = 1")
    User login(String username, String password);

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO sys_user(username, password, real_name, id_number, phone, role, status, create_time) " +
            "VALUES(#{username}, #{password}, #{realName}, #{idNumber}, #{phone}, #{role}, #{status}, #{createTime})")

    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
    
    @Update("UPDATE sys_user SET password = #{password} WHERE username = #{username}")
    int updatePasswordByUsername(String username, String password);
}