package com.lss.education_resources_service.datasource.mapper.febs;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.lss.education_resources_service.bean.entity.febs.RoleMenu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface RoleMenuMapper {

    @Insert("insert into t_role_menu(role_id,menu_id)values(#{roleId},#{menuId})")
    void save(RoleMenu roleMenuDto);

    @Delete("delete from t_role_menu where role_id =#{0}")
    void deleteByRoleId(Integer roleId);


    @Delete("delete from t_role_menu where menu_id =#{0}")
    void deleteByMenuId(Integer menuId);

    @DeleteProvider(type = RoleMenuMapper.CommonProvider.class, method = "deleteByMenuIds")
    void deleteByMenuIds(Set<String> menuSetIds);

    @Select("select menu_id from t_role_menu where role_id =#{0}")
    List<String> queryByRoleId(Integer roleId);

    class CommonProvider {

        public String deleteByMenuIds(Set<Integer> menuSetIds) {
            SQL sql = new SQL().DELETE_FROM("t_role_menu");
            if (!menuSetIds.isEmpty()) {
                filterFieldId(sql, "menu_id", menuSetIds);
            } else {
                sql.WHERE("menu_id =''");
            }

            return sql.toString();
        }


        public void filterFieldId(SQL sql, String fieldName, Set<Integer> menuSetIds) {
            if (menuSetIds.isEmpty()) {
                return;
            }
            StringBuilder menuIds = new StringBuilder();
            for (Integer menuId : menuSetIds) {
                menuIds.append("'" + menuId + "'");
                menuIds.append(StringPool.COMMA);
            }
            String ids = menuIds.toString();
            sql.WHERE(fieldName + "  IN (" + ids.substring(0, ids.length() - 1) + ")");
        }
    }
}
