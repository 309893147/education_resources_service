package com.lss.education_resources_service.datasource.mapper.febs;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.lss.education_resources_service.bean.entity.febs.Menu;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface MenuMapper {

    @Insert("insert into t_menu(menu_id,parent_id,menu_name,url,perms,icon,type,order_num,create_date)" +
            "values(#{menuId},#{parentId},#{menuName},#{url},#{perms},#{icon},#{type},#{orderNum},#{createDate})")
    void save(Menu menuDto);


    @Update("update t_menu set menu_name = #{menuName},icon = #{icon},url = #{url},perms = #{perms}," +
            "order_num =#{orderNum},update_date =#{updateDate} where menu_id =#{menuId}")
    void updateMenu(Menu menuDto);

    @Update("update t_menu set menu_name = #{menuName},perms = #{perms},update_date =#{updateDate} where menu_id =#{menuId}")
    void updateButton(Menu menuDto);


    @Select("select menu_id from t_menu where parent_id =#{0}")
    List<String> getSonMenu(String menuId);

    @DeleteProvider(type = MenuMapper.CommonProvider.class,method = "deleteMenu")
    void delete(Set<String> deptSetIds);


    @Results({
            @Result(property = "menuId", column = "menu_id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "menuName", column = "menu_name"),
            @Result(property = "orderNum", column = "order_num"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate", column = "update_date")
    })
    @SelectProvider(type = MenuMapper.CommonProvider.class,method = "findMenus")
    List<Menu> findMenus(Menu menuDto);


    @Select("SELECT m.* FROM t_role r LEFT JOIN  t_manager_user_role tu on" +
            "(r.role_id=tu.role_id) LEFT JOIN t_role_menu tm ON(r.role_id=tm.role_id)" +
            " LEFT JOIN t_menu m ON(tm.menu_id=m.menu_id) WHERE tu.user_id =#{0} AND m.perms IS NOT NULL AND m.perms!=''")
    List<Menu> findUserPermissions(String userId);

    class CommonProvider {

        public String deleteMenu(Set<String> menuIds){
            SQL sql = new SQL().DELETE_FROM("t_menu");
            if (!menuIds.isEmpty()){
                filterFieldId(sql,"menu_id",menuIds);
            }else {
                sql.WHERE("menu_id =''");
            }
            return sql.toString();
        }

        public String findMenus(Menu menuDto){
            SQL sql = new SQL().SELECT("t.*").FROM("t_menu t").WHERE("1=1");
            if (!StringUtils.isEmpty(menuDto.getMenuName())){
                sql.WHERE("t.menu_name =#{menuName}");
            }
            sql.ORDER_BY("t.order_num");
            return sql.toString();
        }

        public void filterFieldId(SQL sql, String fieldName, Set<String> deptIds) {
            if (deptIds.isEmpty()){
                return;
            }
            StringBuilder sbIds = new StringBuilder();
            for (String deptId : deptIds) {
                sbIds.append("'" + deptId + "'");
                sbIds.append(StringPool.COMMA);
            }
            String ids = sbIds.toString();
            sql.WHERE(fieldName + "  IN (" + ids.substring(0,ids.length()-1) + ")");
        }
    }
}
