package com.education.resources.service.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.ResourceUserDownload;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.from.ResourceScoreFrom;
import com.education.resources.datasource.repository.ResourceUserDownloadRepository;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.service.BaseService;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceUserDownloadService extends BaseService {

    @Autowired
    ResourceUserDownloadRepository resourceUserDownloadRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceRepository resourceRepository;

    public ResourceUserDownload downloadSuccess(ResourceUserDownload resourceUserDownload){
        if (resourceUserDownloadRepository.count(Specifications.<ResourceUserDownload>and().eq("userId",resourceUserDownload.getUserId()).eq("resourceId",resourceUserDownload.getResourceId()).build())>0){
            return null;
        }
        User user = userRepository.findItemById(getCurrentUser().getId());
        user.setIntegral(user.getIntegral()-1);
        userRepository.save(user);
        return resourceUserDownloadRepository.save(resourceUserDownload);
    }

   public Integer canDownloadResource(){
       User user = userRepository.findItemById(getCurrentUser().getId());

       if (user.getIntegral()>1){
           return 1;
       }
       return 0;
   }

    public Page<ResourceUserDownload> getMyDownloadResource(PageForm pageForm){
        PredicateBuilder<ResourceUserDownload> spec = Specifications.<ResourceUserDownload>and().eq("userId", getCurrentUser().getId());
       return resourceUserDownloadRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public ResourceUserDownload  commentScore(ResourceUserDownload resourceUserDownload){
//        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("id", resourceUserDownload.getResourceId());
//
//        List<Resource> resourceL = resourceRepository.findAll(spec.build());
        resourceUserDownload.setUserId(getCurrentUser().getId());
       return resourceUserDownloadRepository.save(resourceUserDownload);
    }


}
