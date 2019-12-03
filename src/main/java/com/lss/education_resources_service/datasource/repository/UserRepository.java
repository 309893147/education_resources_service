package com.lss.education_resources_service.datasource.repository;

import com.lss.education_resources_service.bean.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends BaseRepository<User>{


    //HQL语句中表名应该是ORM映射的类名,而不是你在数据库中的表名
    @Query("select u from User u where u.mobile=:mobile")
    User  queryByMobile(@Param("mobile")String mobile);
}
