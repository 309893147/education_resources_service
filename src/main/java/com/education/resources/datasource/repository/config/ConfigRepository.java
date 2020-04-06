package com.education.resources.datasource.repository.config;

import com.education.resources.bean.config.Config;
import com.education.resources.datasource.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends BaseRepository<Config> {

    @Query("select item from Config  item where name = :name")
    Config  findByName(@Param("name") String name);
}
