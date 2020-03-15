package com.education.resources.service.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.ResourceComment;
import com.education.resources.bean.from.PageForm;
import com.education.resources.datasource.repository.resource.ResourceCommentRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.service.BaseService;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ResourceCommentService  extends BaseService {

    @Autowired
    ResourceCommentRepository resourceCommentRepository;

    public Page<ResourceComment> getComment(ResourceComment resourceComment, PageForm pageForm){
        PredicateBuilder<ResourceComment> spec = Specifications.<ResourceComment>and().eq("presenceStatus", 1);
//        spec.eq(resourceComment.getBasicTypeId()!=null,"basicTypeId",resource.getBasicTypeId());
        spec.eq("resourceId",resourceComment.getResourceId());
        return resourceCommentRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public ResourceComment  getOne(Integer id){
        return resourceCommentRepository.findItemById(id);
    }

    public ResourceComment save(ResourceComment resourceComment) {
        return resourceCommentRepository.save(resourceComment);
    }


    public void delete(Integer id) {
        ResourceComment item = resourceCommentRepository.findItemById(id);
        item.setPresenceStatus(0);
        resourceCommentRepository.save(item);
    }
}
