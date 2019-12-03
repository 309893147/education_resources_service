package com.lss.education_resources_service.datasource.mapper.febs;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lss.education_resources_service.bean.entity.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Mapper
public interface ManagerUserMapper{

    @Insert("insert into manager_user(user_id,username,password,dept_id,email,mobile,status,create_date," +
            "update_date,last_login_date,ssex,tab,theme,avatar,description)values" +
            "(#{userId},#{username},#{password},#{deptId},#{email},#{mobile},#{status},#{createDate},#{updateDate},#{lastLoginDate}," +
            "#{sex},#{tab},#{theme},#{avatar},#{description})")
    void save(User managerUserDto);


    @Delete("delete from manager_user where user_id =#{0}")
    void delete(String userId);


    @Update("update manager_user set password =#{0},update_date =#{updateDate} where id =#{1}")
    void updatePassword(String password, String userId);


    @Update("update manager_user set username =#{username},mobile =#{mobile}," +
            "email =#{email},dept_id =#{deptId},status=#{status},description =#{description} where user_id =#{userId}")
    void update(User managerUserDto);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deptId", column = "dept_id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate ", column = "update_date"),
            @Result(property = "lastLoginDate", column = "last_login_date")
    })
    @Select("select * from manager_user where user_id =#{0}")
    User queryById(String userId);


    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deptId", column = "dept_id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate", column = "update_date"),
            @Result(property = "lastLoginDate", column = "last_login_date")
    })
    @Select("select * from manager_user where username =#{0}")
   User queryByName(String username);

    @Select("SELECT * FROM user  order by create_time DESC")
    List<User> queryByPage(Page<User> page);


//    @Results({
//            @Result(property = "userId", column = "user_id"),
//            @Result(property = "deptId", column = "dept_id"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "updateDate", column = "update_date"),
//            @Result(property = "lastLoginDate", column = "last_login_date")
//    })
//    @SelectProvider(type= ManagerUserMapper.CommonProvider.class,method="queryByPage")
//    List<ManagerUserDto> queryByPage(ManagerUserDto managerUserDto);
//
//    class CommonProvider extends BaseProvider {
//
//        public String queryByPage(ManagerUserDto m){
//            SQL sql = new SQL().SELECT("m.*").FROM("manager_user m").WHERE("1=1");
//            if (!StringUtils.isEmpty(m.getUserId())){
//                sql.WHERE("m.user_id =#{userId}");
//            }
//            if (!StringUtils.isEmpty(m.getUsername())){
//                sql.WHERE("m.username like concat('%',#{username},'%')");
//            }
//            if (!StringUtils.isEmpty(m.getMobile())){
//                sql.WHERE("m.mobile =#{mobile}");
//            }
//            if (!StringUtils.isEmpty(m.getStatus())){
//                sql.WHERE("m.status =#{status}");
//            }
//
//            return  sql.toString();
//        }
//    }


}
