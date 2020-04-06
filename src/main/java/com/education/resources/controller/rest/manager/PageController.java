package com.education.resources.controller.rest.manager;


import com.education.resources.bean.entity.Banner;
import com.education.resources.bean.entity.config.SystemConfig;
import com.education.resources.bean.entity.notice.Notice;
import com.education.resources.service.BannerService;
import com.education.resources.service.config.ConfigService;
import com.education.resources.service.notifaction.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page")
public class PageController {

    private NoticeService noticeService;

    private BannerService bannerService;

    @Autowired
    private ConfigService configService;



    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @Autowired
    public void setInforKnowledgeService(BannerService bannerService) {
        this.bannerService = bannerService;
    }


    @GetMapping("/notice/{id}")
    public ModelAndView modelAndView(@PathVariable("id")int id){
        Notice item = noticeService.getOne(id);
        return createPage(item.getTitle(),item.getContent());
    }


    @GetMapping("/banner/{id}")
    public ModelAndView bannerPage(@PathVariable("id") int id){
        Banner item = bannerService.getOne(id);
        return createPage(item.getTitle(),item.getContent());
    }

    @GetMapping("/about/us")
    public ModelAndView aboutUsPage(){
        String content = configService.getConfig(SystemConfig.class).getAboutUs().getValue();
        return createPage(content);
    }

    private ModelAndView createPage(String  title, String  content){
        ModelAndView modelAndView = new ModelAndView("web");
        modelAndView.addObject("title",title);
        modelAndView.addObject("content",content);
        return modelAndView;
    }

    private ModelAndView createPage(String  content){
        ModelAndView modelAndView = new ModelAndView("web");
        modelAndView.addObject("content",content);
        return modelAndView;
    }


}
