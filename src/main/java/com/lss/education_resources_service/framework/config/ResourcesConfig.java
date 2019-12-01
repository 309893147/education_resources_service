package com.lss.education_resources_service.framework.config;


import com.lss.education_resources_service.annotation.AnonUrl;
import com.lss.education_resources_service.framework.shiro.UrlItem;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 通用配置
 * 
 * @author ruoyi
 */
@Configuration
@Component
public class ResourcesConfig extends WebMvcConfigurationSupport implements ApplicationListener<ApplicationReadyEvent>
{

    private MediaConfig mediaConfig;

    public ResourcesConfig(MediaConfig mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    public static Set<UrlItem> anonUrls = new HashSet<>();




    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        super.addResourceHandlers(registry);
        /** swagger配置 */
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Map<RequestMappingInfo, HandlerMethod> mapping = requestMappingHandlerMapping().getHandlerMethods();
        mapping.forEach(new BiConsumer<RequestMappingInfo, HandlerMethod>() {
            @Override
            public void accept(RequestMappingInfo requestMappingInfo, HandlerMethod handlerMethod) {
                for (String url:requestMappingInfo.getPatternsCondition().getPatterns()){
                    AnonUrl anon = handlerMethod.getMethod().getAnnotation(AnonUrl.class);
                    if (anon != null){
                        Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                        for (RequestMethod method:methods){
                            UrlItem  urlItem = new UrlItem();
                            urlItem.setMethod(method.name());
                            urlItem.setUrl(url);
                            anonUrls.add(urlItem);
                        }
                    }
                }

            }
        });
    }
}