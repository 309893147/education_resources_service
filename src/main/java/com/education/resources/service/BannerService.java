package com.education.resources.service;

import com.education.resources.bean.entity.Banner;
import com.education.resources.bean.from.PageForm;
import com.education.resources.datasource.repository.BannerRepository;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends BaseService {

    @Autowired
    BannerRepository bannerRepository;

    public Page bannerPage(Banner banner, PageForm pageForm) {
        PredicateBuilder<Banner> spec = SpecificationUtil.filter(banner, pageForm);
        return bannerRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public List<Banner> bannerList() {
        PredicateBuilder<Banner> spec = Specifications.<Banner>and();
        spec.eq("presenceStatus", 1);
        spec.eq("joinUse", true);
        return bannerRepository.findAll(spec.build());
    }

    public Banner getOne(Integer id) {
        return bannerRepository.findItemById(id);
    }


    public Banner saveBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    public void deleteBanner(String ids) {
        bannerRepository.softDelete(StringUtil.toIntArray(ids));
    }


}
