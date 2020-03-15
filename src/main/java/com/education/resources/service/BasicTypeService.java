package com.education.resources.service;

import com.education.resources.bean.entity.BasicType;
import com.education.resources.bean.from.PageForm;
import com.education.resources.datasource.repository.BasicTypeRepository;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicTypeService {

    @Autowired
    BasicTypeRepository basicTypeRepository;

//    @Autowired
//    BasicTypeMapper basicTypeMapper;
//
//    public List<BasicType> enumTypeList(String type) {
//        return basicTypeMapper.enumTypeList(type);
//    }

    public Page<BasicType> list(BasicType basicType, PageForm pageForm) {
        PredicateBuilder<BasicType> spec = SpecificationUtil.filter(basicType, pageForm);
        return basicTypeRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public  List<BasicType> typePage(BasicType basicType, PageForm pageForm){
       return basicTypeRepository.findAll(Specifications.<BasicType>and().eq("type",basicType.getType()).build());
    }

    public BasicType getOne(Integer id) {
        return basicTypeRepository.findItemById(id);
    }

    public BasicType save(BasicType basicType) {
        return basicTypeRepository.save(basicType);
    }

    public void delete(String ids) {
        basicTypeRepository.deleteInBatch(StringUtil.toDeleteBatch(ids,BasicType.class));
    }

}
