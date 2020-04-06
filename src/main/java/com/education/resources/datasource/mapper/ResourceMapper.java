package com.education.resources.datasource.mapper;

import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.vo.IndexTicketTop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface ResourceMapper  {

    @Select("select ip,create_time from manager_login_log where manager_id=#{managerId}  order BY create_time DESC limit 2")
    List<ManagerLoginLog> findServiceData(@Param("managerId") Integer managerId);

    @Select("select r.* from resource r order by r.click_number DESC  Limit 5 ")
    List<Resource> lookList();

    @Select("select r.* from resource r where r.recommend=true order by r.create_time DESC  Limit 5 ")
    List<Resource> recommendList();

    @Select("select r.* from resource r  order by r.create_time DESC  Limit 5 ")
    List<Resource> newList();

    @SelectProvider(type = ResourceMapper.TopProvider.class,method = "findTicketTop")
    List<IndexTicketTop> findTicketTopPerson( @Param("startTime") String startTime, @Param("endTime") String endTime);

    class TopProvider extends BaseProvider {

        public String findTicketTop(String startTime, String endTime) {
            SQL sql = new SQL().SELECT("em.name as name,COUNT(tu.manager_id) as ticket_number").FROM("resource tu LEFT JOIN manager em ON tu.manager_id = em.id");

//            sql.WHERE(" tu.status = 'ACCEPT' AND tu.role = 'HANDLER' ");
            sql.WHERE("tu.create_time between #{startTime} and #{endTime}");
            sql.GROUP_BY("tu.manager_id");
            sql.ORDER_BY("count(tu.manager_id) DESC LIMIT 3");
            return sql.toString();
        }
    }
}
