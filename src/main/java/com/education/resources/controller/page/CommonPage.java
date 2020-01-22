//package com.lss.education_resources_service.controller.page;
//
//
//import com.cq1080.web.entity.WebPage;
//import com.cq1080.web.repository.WebPageRepository;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@Api
//public class CommonPage {
//
//    private WebPageRepository  mWebPageRepository;
//
//    public CommonPage(WebPageRepository webPageRepository) {
//        this.mWebPageRepository = webPageRepository;
//    }
//
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//    @GetMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }
//
//    @ApiOperation(value = "一些静态富文本界面，关于我们，用户协议等等",notes = "name 即是相关页面的名字")
//    @GetMapping("/page/{name}.html")
//    public ModelAndView mediaText(@PathVariable String name){
//        ModelAndView modelAndView = new ModelAndView("webPage");
//        WebPage content = mWebPageRepository.findByName(name);
//        if (content == null){
//            content = new WebPage();
//        }
//        modelAndView.addObject("content",content);
//        return modelAndView;
//    }
//
//}
