package com.education.resources.service.notifaction;

import com.education.resources.bean.entity.Log;
import com.education.resources.bean.entity.config.DingConfig;
import com.education.resources.bean.entity.notice.Notice;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.notication.DingMsgForm;
import com.education.resources.bean.pojo.event.DingMessageEvent;
import com.education.resources.bean.pojo.event.LogEvent;
import com.education.resources.datasource.repository.NoticeRepository;
import com.education.resources.service.BaseService;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NoticeService extends BaseService {

    @Autowired
    private NoticeRepository noticeRepository;

    private DingMsgService dingMsgService = new DingMsgService();

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

    public Notice  getOne(Integer id){
        return noticeRepository.findItemById(id);
    }


    public Page<Notice> getApiNoticeList(PageForm pageForm) {
        PredicateBuilder<Notice> spec = Specifications.<Notice>and().eq("presenceStatus", 1);
        return noticeRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    /**
     * 发送钉钉消息
     * @param event
     */
    @Async
    @EventListener(DingMessageEvent.class)
    public void sendDingTalkMessage(DingMessageEvent event){
        String webHook = getConfig(DingConfig.class).getWebHook().stringValue();
        if(StringUtils.isEmpty(webHook)){
            sendEvent(LogEvent.builder().level(Log.Level.WARNING).content("没有设置钉钉推送链接"));
            return;
        }
        DingMsgForm  form = new DingMsgForm();
        form.setDingType(DingMsgForm.DingType.TEXT);
        form.setContent(event.getContent());
        form.setKeyWord("小优机器人");
        if (!StringUtils.isEmpty(event.getMention())){
            form.setMobiles(event.getMention());
        }
        form.setWebhook(webHook);
        try{
            dingMsgService.sendMsg(form);
        }catch (Exception e){
           e.printStackTrace();
        }

    }

//    public void readNotice(Integer id) {
//        noticeRepository.updateReadCount(id);
//    }
}

