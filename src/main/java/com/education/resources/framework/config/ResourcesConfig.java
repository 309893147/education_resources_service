package com.education.resources.framework.config;


import com.lss.auth.config.AuthResourceConfig;
import com.lss.auth.repository.PermissionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * 通用配置
 *
 * @author ruoyi
 */
@Configuration
@Component
public class ResourcesConfig extends AuthResourceConfig implements ApplicationListener<ApplicationReadyEvent>
{

    private MediaConfig mediaConfig;

    public ResourcesConfig(PermissionRepository permissionRepository, MediaConfig mediaConfig) {
        super(permissionRepository);
        this.mediaConfig = mediaConfig;
    }

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
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");

    }
}

