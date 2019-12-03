package com.lss.education_resources_service.datasource.mapper.febs;

import com.lss.education_resources_service.bean.entity.febs.Role;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Mapper
public interface RoleMapper {

    @Insert("insert into t_role(role_id,role_name,remark,create_date,update_date)values" +
            "(#{roleId},#{roleName},#{remark},#{createDate},#{updateDate})")
    void save(Role roleDto);

    @Delete("delete from t_role where role_id =#{0}")
    void delete(Integer roleId);


    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate", column = "update_date")
    })
    @SelectProvider(type = RoleMapper.CommonProvider.class,method = "queryByPage")
    List<Role> queryByPage(Role roleDto);

    @Update("update t_role set role_name =#{roleName},remark =#{remark} where role_id =#{roleId}")
    void update(Role roleDto);

    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate", column = "update_date")
    })
    @Select("SELECT r.* FROM t_manager_user_role t LEFT JOIN t_role r ON(t.role_id =r.role_id) WHERE t.user_id =#{0}")
    List<Role> findUserRole(Integer userId);

    class  CommonProvider {
        public String queryByPage(Role roleDto){
            SQL sql = new SQL().SELECT("t.*").FROM("t_role t").WHERE("1=1");
            if (!StringUtils.isEmpty(roleDto.getRoleName())){
                sql.WHERE("t.role_name =#{roleName}");
            }
            if (!StringUtils.isEmpty(roleDto.getId())){
                sql.WHERE("t.role_id =#{roleId}");
            }
            sql.ORDER_BY("t.create_date");
            return sql.toString();

        }
    }

}
