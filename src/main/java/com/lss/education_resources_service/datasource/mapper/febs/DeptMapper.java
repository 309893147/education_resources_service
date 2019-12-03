//package com.lss.education_resources_service.datasource.mapper.febs;
//
//import jodd.util.StringPool;
//import org.apache.ibatis.annotations.*;
//import org.apache.ibatis.jdbc.SQL;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//
//@Component
//public interface DeptMapper {
//
//    @Insert("insert into t_dept(dept_id,parent_id,dept_name,order_num,create_date,update_date)" +
//            "values(#{deptId},#{parentId},#{deptName},#{orderNum},#{createDate},#{updateDate})")
//    void save(DeptDto deptDto);
//
//
//
//    @DeleteProvider(type = DeptMapper.CommonProvider.class,method = "deleteDept")
//    void delete(Set<String> deptIds);
//
//
//    @Update("update t_dept set parent_id = #{parentId},dept_name = #{deptName},order_num = #{orderNum},update_date= #{updateDate} where dept_id =#{deptId}")
//    void update(DeptDto deptDto);
//
//
//    @Select("select dept_id from t_dept where parent_id =#{0}")
//    List<String> getSonDeptId(String deptId);
//
//
//    @Results({
//            @Result(property = "deptId", column = "dept_id"),
//            @Result(property = "parentId", column = "parent_id"),
//            @Result(property = "deptName", column = "dept_name"),
//            @Result(property = "orderNum", column = "order_num"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "updateDate", column = "update_date")
//
//    })
//    @Select("select * from t_dept  order by order_num")
//    List<DeptDto> getDeptTree();
//
//    @Results({
//            @Result(property = "deptId", column = "dept_id"),
//            @Result(property = "parentId", column = "parent_id"),
//            @Result(property = "deptName", column = "dept_name"),
//            @Result(property = "orderNum", column = "order_num"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "updateDate", column = "update_date")
//
//    })
//    @Select("select * from t_dept where dept_name =#{0} order by order_num")
//    List<DeptDto> getSontree(String deptName);
//
//
//    class CommonProvider {
//
//        public String deleteDept(Set<String> deptIds){
//            SQL sql = new SQL().DELETE_FROM("t_dept");
//            if (!deptIds.isEmpty()){
//                filterFieldId(sql,"dept_id",deptIds);
//            }else {
//                sql.WHERE("dept_id =''");
//            }
//            return sql.toString();
//        }
//
//        public void filterFieldId(SQL sql, String fieldName, Set<String> deptIds) {
//            if (deptIds.isEmpty()){
//                return;
//            }
//            StringBuilder sbIds = new StringBuilder();
//            for (String deptId : deptIds) {
//                sbIds.append("'" + deptId + "'");
//                sbIds.append(StringPool.COMMA);
//            }
//            String ids = sbIds.toString();
//            sql.WHERE(fieldName + "  IN (" + ids.substring(0,ids.length()-1) + ")");
//        }
//    }
//
//}
