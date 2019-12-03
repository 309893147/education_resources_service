//package com.lss.education_resources_service.controller.rest.manager.user;
//
//import com.zhongchou.controller.BaseController;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BiConsumer;
//
//@RestController
//@RequestMapping("/manager/user")
//public class EndpointDocController  {
//    private final RequestMappingHandlerMapping handlerMapping;
//
//    @Autowired
//    public EndpointDocController(RequestMappingHandlerMapping handlerMapping) {
//        this.handlerMapping = handlerMapping;
//    }
//
////    @GetMapping(value = "/urls",name = "获取连接")
////    @RequiresPermissions(value = "user:add")
////    public String show() {
////        Map<RequestMappingInfo, HandlerMethod> info = this.handlerMapping.getHandlerMethods();
////
////        info.forEach(new BiConsumer<RequestMappingInfo, HandlerMethod>() {
////            @Override
////            public void accept(RequestMappingInfo mapping, HandlerMethod method) {
////
//////                s=mapping.getPatternsCondition().getPatterns();
////                for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
//////                    System.out.println(urlPattern);
////                    System.out.println(
////                            method.getBeanType().getName() + "#" + method.getMethod().getName() +
////                                    " <-- " + urlPattern);
////
////                    if (urlPattern.equals("some specific url")) {
////                        //add to list of matching METHODS
////                    }
////                }
////            }
////        });
////
////        return successResult();
////    }
//
//    @RequiresPermissions(value = "user:add")
//    @GetMapping(value = "/urls",name = "获取连接")
//    public String show() {
//        Map<RequestMappingInfo, HandlerMethod> info = this.handlerMapping.getHandlerMethods();
//        List list= new ArrayList();
//        info.forEach(new BiConsumer<RequestMappingInfo, HandlerMethod>() {
//            @Override
//            public void accept(RequestMappingInfo mapping, HandlerMethod method) {
//
////                s=mapping.getPatternsCondition().getPatterns();
//                for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
////                    System.out.println(urlPattern);
//                    list.add(urlPattern);
////                    System.out.println(
////                            method.getBeanType().getName() + "#" + method.getMethod().getName() +
////                                    " <-- " + urlPattern);
////
////                    if (urlPattern.equals("some specific url")) {
////                        //add to list of matching METHODS
////                    }
//                }
//            }
//        });
//
//        return successResult(list);
//    }
//}