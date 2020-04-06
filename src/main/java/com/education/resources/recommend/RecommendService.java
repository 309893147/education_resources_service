package com.education.resources.recommend;

import com.education.resources.bean.entity.Recommend;
import com.education.resources.datasource.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendService implements Serializable {

    @Autowired
    RecommendRepository recommendRepository;

    //召回数据,根据userId 召回ResourceList
    public List<Integer> recall(Integer userId){
        Recommend item = recommendRepository.findItemById(userId);

        if (item == null){
            item = recommendRepository.findItemById(999999);
        }

        String[] resArr = item.getRecommend().split(",");
        List<Integer> resList = new ArrayList<>();
       for (int i = 0;i<resArr.length;i++){
           resList.add(Integer.valueOf(resArr[i]));

       }
       return  resList;
    }

}
