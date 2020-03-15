package com.education.resources.service.notifaction;



import com.education.resources.bean.entity.notice.Notice;
import com.education.resources.bean.from.PageForm;
import com.education.resources.datasource.repository.NoticeRepository;
import com.education.resources.service.BaseService;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NoticeService extends BaseService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Page<Notice> list(Notice notice, PageForm pageForm) {
        PredicateBuilder<Notice> spec = SpecificationUtil.filter(notice, pageForm);
        return noticeRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public Notice noticeSave(Notice notice){
        return noticeRepository.save(notice);
    }

    public void noticeDelete(String ids){
        noticeRepository.softDelete(StringUtil.toIntArray(ids));
    }
}

