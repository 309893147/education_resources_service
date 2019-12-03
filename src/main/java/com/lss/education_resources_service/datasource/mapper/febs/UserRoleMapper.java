package com.lss.education_resources_service.datasource.mapper.febs;

import com.lss.education_resources_service.bean.entity.febs.UserRole;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserRoleMapper {


    @Insert("insert into t_manager_user_role(user_id,role_id)values(#{userId},#{roleId})")
    void save(UserRole userRoleDto);

    @Delete("delete from t_manager_user_role where user_id =#{0}")
    void deleteByUserId(String userId);

    @Delete("delete from t_manager_user_role where role_id =#{0}")
    void deleteByRoleId(String roleId);

}
