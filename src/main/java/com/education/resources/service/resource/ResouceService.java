package com.education.resources.service.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.ResourceUserDownload;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.pojo.event.DingMessageEvent;
import com.education.resources.datasource.mapper.ResourceMapper;
import com.education.resources.datasource.repository.ResourceUserDownloadRepository;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.recommend.RecommendService;
import com.education.resources.recommend.RecommendSortService;
import com.education.resources.recommend.ResourceSortModel;
import com.education.resources.service.BaseService;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.glassfish.jersey.server.model.ResourceModel;
import org.spark_project.jetty.util.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class ResouceService extends BaseService {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RecommendService recommendService;

    @Autowired
    RecommendSortService recommendSortService;

    @Autowired
    UserRepository userRepository;


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

    public List<Resource> recommend(){
        List<Integer> resourceIdList = recommendService.recall(getCurrentUser().getId());
        List<Integer> LrResourceIdList=  recommendSortService.sort(resourceIdList,getCurrentUser().getId(),"LR");
        List<Integer> GbdtresourceIdList=  recommendSortService.sort(resourceIdList,getCurrentUser().getId(),"GBDT");
        List<Resource> LRShopModelList = LrResourceIdList.stream().map(id->{
            Resource resource = get(id);
            resource.setOperator("LR");
            return resource;
        }).collect(Collectors.toList());
        List<Resource> GBDTShopModelList = GbdtresourceIdList.stream().map(id->{
            Resource resource = get(id);
            resource.setOperator("GBDT");
            return resource;
        }).collect(Collectors.toList());

        //固定输出6个
        List<Resource> resultShopModelList = new ArrayList<>();
        Queue<Resource> LRQueue = new ArrayQueue<>();
        LRQueue.addAll(LRShopModelList);
        Queue<Resource> GBDTQueue = new ArrayQueue<>();
        GBDTQueue.addAll(GBDTShopModelList);
        for(int i = 0; i < 6; i++){
            Resource addShopModel = null;
            if( i % 2 == 0){
                //选取LR
                addShopModel = LRQueue.poll();
                boolean isRepeat = false;
                for(int j = 0; j < resultShopModelList.size();j++){
                    if(addShopModel.getId().equals(resultShopModelList.get(j).getId())){
                        isRepeat = true;
                        break;
                    }
                }
                if(isRepeat){
                    i--;
                    continue;
                }
            }else if(i % 2 == 1){
                //选取GBDT
                addShopModel = GBDTQueue.poll();
                boolean isRepeat = false;
                for(int j = 0; j < resultShopModelList.size();j++){
                    if(addShopModel.getId().equals(resultShopModelList.get(j).getId())){
                        isRepeat = true;
                        break;
                    }
                }
                if(isRepeat){
                    i--;
                    continue;
                }
            }
            resultShopModelList.add(addShopModel);
        }

        return resultShopModelList;
    }


    public Resource get(Integer id) {
        Resource resource = resourceRepository.findItemById(id);
        if(resource == null){
            return null;
        }
//        resource.setSellerModel(sellerService.get(shopModel.getSellerId()));
//        resource.setCategoryModel(categoryService.get(shopModel.getCategoryId()));
        return resource;
    }

    public List<Resource> getBdResource(Integer status){
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        if (status==1){
            return resourceMapper.lookList();
        }
        if (status ==2){
            return resourceMapper.recommendList();
        }
        return resourceMapper.newList();
    }

    public Page<Resource> getMyResource(PageForm pageForm){
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        spec.eq("userId",getCurrentUser().getId());
        return resourceRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public Resource  getOne(Integer id){
        Resource resource = resourceRepository.findItemById(id);
        resource.setClickNumber(resource.getClickNumber()+1);
        return resourceRepository.save(resource);
    }

    public Resource resourceSave(Resource resource) {
        resource.setResourceStatus(Resource.ResourceStatus.PASS);
        resource.setManagerId(getCurrentManager().getId());
        return resourceRepository.save(resource);
    }

    public Resource resourceReview(Resource resource){
        if (resource.getResourceStatus().equals(Resource.ResourceStatus.PASS)){
            User user = userRepository.findItemById(resource.getUserId());
            user.setIntegral(user.getIntegral()+5);
            userRepository.save(user);
        }
        return resourceRepository.save(resource);
    }

    public Resource wxResourceAdd(Resource resource) {
//        resource.setPresenceStatus(1);
        resource.setUserId(getCurrentUser().getId());
        resource.setResourceStatus(Resource.ResourceStatus.UNPROCESSED);
        sendEvent(DingMessageEvent.builder().content("\n用户:"+getCurrentUser().getNickName()+"添加了资源:\n"+resource.getTitle()+"\n请管理员尽快处理").build());
        return resourceRepository.save(resource);
    }


    public void resourceDelete(String ids) {
        resourceRepository.softDelete(StringUtil.toIntArray(ids));
    }

    public void resourceDelete(Integer id) {
        Resource item = resourceRepository.findItemById(id);
        item.setPresenceStatus(0);
        resourceRepository.save(item);
    }

}
