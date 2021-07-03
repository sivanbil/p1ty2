package org.example.dinner.oauth2.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.dinner.commons.model.pojo.DinnerUser;

public interface UserMapper {
    @Select("select id, username, nickname, password, phone, email,avatar, is_valid ,roles from" +
            " s_user where username=#{account} or phone=#{account} or email=#{account}")
    DinnerUser selectByAccountInfo(@Param("account") String account);
}
