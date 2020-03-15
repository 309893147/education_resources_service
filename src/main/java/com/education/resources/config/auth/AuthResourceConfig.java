package com.education.resources.config.auth;

import com.education.resources.annotation.AnonUrl;
import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.auth.Menu;
import com.education.resources.bean.auth.Permission;
import com.education.resources.bean.auth.UrlItem;
import com.education.resources.datasource.repository.auth.PermissionRepository;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.*;
import java.util.function.BiConsumer;


public class AuthResourceConfig extends WebMvcConfigurationSupport implements ApplicationListener<ApplicationReadyEvent> {

    public static Set<UrlItem> anonUrls = new HashSet<>();

    private PermissionRepository permissionRepository;


    public AuthResourceConfig(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Map<RequestMappingInfo, HandlerMethod> mapping = requestMappingHandlerMapping().getHandlerMethods();
        mapping.forEach(new BiConsumer<RequestMappingInfo, HandlerMethod>() {
            @Override
            public void accept(RequestMappingInfo requestMappingInfo, HandlerMethod handlerMethod) {
                requestMappingInfo.getPatternsCondition().getPatterns().forEach(url ->{
                    requestMappingInfo.getMethodsCondition().getMethods().forEach(method ->{
                        processAnonUrl(method.name(),url,handlerMethod);
                        processManagePermission(method.name(),url,handlerMethod);
                    });
                });
            }
        });


    }

    private void processAnonUrl(String method, String url, HandlerMethod requestMethod){
        AnonUrl anon = requestMethod.getMethod().getAnnotation(AnonUrl.class);
        if (anon == null){
            return;
        }
        UrlItem urlItem = new UrlItem();
        urlItem.setMethod(method);
        urlItem.setUrl(url);
        anonUrls.add(urlItem);
    }


    /**
     *
     */
    public static Map<String, Menu>  menuMap = new HashMap<>();

    public static List<Menu> menuList = new ArrayList<>();

    public static List<Permission>  permissions = new ArrayList<>();

    private void processManagePermission(String method,String url, HandlerMethod processMethod){
        if (!url.startsWith("/manage")){
            return;
        }
        PermissionDes classPermission = processMethod.getBeanType().getAnnotation(PermissionDes.class);
        PermissionDes methodPermission = processMethod.getMethod().getAnnotation(PermissionDes.class);
        if (methodPermission == null){
            return;
        }
        String[] menu = getMenu(classPermission, methodPermission);
        if (menu == null  || menu.length ==0){
            return;
        }
        Menu lastMenu = getLastMenu(menu);
        if (lastMenu == null){
            return;
        }
        Permission permissionItem = new Permission();
        permissionItem.setName(methodPermission.name());
        permissionItem.setUrl(url);
        permissionItem.setMethod(method);
        permissionItem.setPath(String.join(",",menu));
        permissions.add(permissionItem);
        try{
            Permission savedOne = permissionRepository.findOne(Specifications.<Permission>and().eq("method", method).eq("url", url).eq("path", permissionItem.getPath()).build()).orElse(null);
            if (savedOne == null){
                savedOne = permissionRepository.save(permissionItem);
            }
            lastMenu.getPermissions().add(savedOne);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Menu  getLastMenu(String[] menu){
        Menu  lastMenu = null;
        for (int i=0;i<menu.length;i++){
            String subMenu = String.join(",", (String[]) ArrayUtils.subarray(menu,0,i+1));
            Menu menuItem = menuMap.get(subMenu);
            if (menuItem == null){
                menuItem = new Menu();
                menuItem.setName(menu[i]);
                menuItem.setSub(new HashSet<>());
                menuItem.setPath(subMenu);
                menuItem.setPermissions(new ArrayList<>());
                menuMap.put(subMenu,menuItem);
                if (i == 0){
                    menuList.add(menuItem);
                }
            }
            if (lastMenu != null){
                lastMenu.getSub().add(menuItem);
            }
            lastMenu = menuItem;
        }
        return lastMenu;
    }

    private String[]  getMenu(PermissionDes classPermission, PermissionDes methodPermission){
        if (classPermission == null || classPermission.menu().length ==0){
            return methodPermission.menu();
        }
        if (methodPermission.rewriteMenu()){
            return methodPermission.menu();
        }
        return (String[]) ArrayUtils.addAll(classPermission.menu(),methodPermission.menu());
    }
}
