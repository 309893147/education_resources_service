package com.education.resources.service;

import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.config.SystemConfig;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.vo.IndexCount;
import com.education.resources.bean.vo.IndexTicketTop;
import com.education.resources.datasource.mapper.ResourceMapper;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.util.MapBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ManageDashboardService extends BaseService{

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    UserRepository userRepository;

    public Map getIndexData() {
        Map dataMap = new HashedMap();
        IndexCount indexCount = new IndexCount();

        Integer unTicket = countIndexTicket(Resource.ResourceStatus.UNPROCESSED);

        Integer todayTicket = countIndexTotalTicket();

        //未处理报事
        indexCount.setUnResourceCount(unTicket);

        //今日新增报事
        indexCount.setTodayResourceCount(todayTicket);
        //接单量
        indexCount.setTodayNewCount(countTodayNewTicket());

        if (getCurrentManager().getType().equals(Manager.Type.SUPER_MANAGER)) {
            //今日新增用户
            Integer newUserCount = Math.toIntExact(userRepository.count(Specifications.<User>and().between("createTime", new Timestamp(startTime()), new Timestamp(endTime())).build()));
            indexCount.setNewUserCount(newUserCount);

            //总用户
            Integer totalUserCount = Math.toIntExact(userRepository.count(Specifications.<User>and().eq("presenceStatus",1).build()));
            indexCount.setUserTotalCount(totalUserCount);
        }
        Long startTime=startTime();
        Long endTime=endTime();
        List<IndexTicketTop> ticketTopPerson = resourceMapper.findTicketTopPerson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime)
                ,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        dataMap.put("indexCount", indexCount);
        dataMap.put("ticketTopPersons", ticketTopPerson);
        return dataMap;
    }

    //接单量
    private Integer countTodayNewTicket() {
        return Math.toIntExact(resourceRepository.count(Specifications.<Resource>and().between("createTime", new Timestamp(startTime()), new Timestamp(endTime())).build()));
    }

    //未处理报事
    public Integer countIndexTicket(Resource.ResourceStatus status) {
        return Math.toIntExact(resourceRepository.count(Specifications.<Resource>and().eq("resourceStatus", status).build()));
    }



    public Integer countIndexTotalTicket() {
        Specification<Resource> spec = Specifications.<Resource>and().between("createTime", new Timestamp(startTime()), new Timestamp(endTime())).build();
        return Math.toIntExact(resourceRepository.count(spec));
    }

    public long startTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, 23, 59, 59);
        long tt = calendar.getTime().getTime();
        return tt;
    }

    public long endTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        long tt = calendar.getTime().getTime();
        return tt;
    }


    public SystemConfig getServiceData() {
        return getConfig(SystemConfig.class);
    }

    public Map<String,Object> getLoginData() {
        List<ManagerLoginLog> serviceData = resourceMapper.findServiceData(getCurrentManager().getId());
        String ip1 = serviceData.size()>0?serviceData.get(0).getIp():"";
        String ip2  = serviceData.size()>1?serviceData.get(1).getIp():"";
        return new MapBuilder<String,Object>()
                .put("bc", StringUtils.isEmpty(ip1)?"":ip1)
                .put("sc",StringUtils.isEmpty(ip2)?"":ip1).put("scTime",serviceData.size()>1?serviceData.get(1).getCreateTime():"")
                .build();
    }


}
