package com.education.resources.service.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.from.PageForm;
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
public class ResouceService extends BaseService {

    @Autowired
    ResourceRepository resourceRepository;

    public Page<Resource> resourcesPage(Resource resource, PageForm pageForm) {
        PredicateBuilder<Resource> spec = SpecificationUtil.filter(resource);
        return resourceRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public Page<Resource> getResource(Resource resource, PageForm pageForm){
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        spec.eq(resource.getBasicTypeId()!=null,"basicTypeId",resource.getBasicTypeId());
        spec.eq("resourceStatus",Resource.ResourceStatus.PASS);
        return resourceRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public Resource  getOne(Integer id){
        return resourceRepository.findItemById(id);
    }

    public Resource resourceSave(Resource resource) {
        resource.setResourceStatus(Resource.ResourceStatus.PASS);
        return resourceRepository.save(resource);
    }

    public Resource resourceReview(Resource resource){
        return resourceRepository.save(resource);
    }

    public Resource wxResourceAdd(Resource resource) {
        resource.setPresenceStatus(1);
        resource.setBasicTypeId(183);
        return resourceRepository.save(resource);
    }


    public void resourceDelete(String ids) {
        resourceRepository.softDelete(StringUtil.toIntArray(ids));
    }

}
