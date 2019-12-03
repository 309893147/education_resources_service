package com.lss.education_resources_service.controller.rest.manager.user;

import com.lss.education_resources_service.bean.entity.febs.Role;
import com.lss.education_resources_service.service.febs.RoleService;
import com.lss.education_resources_service.util.respons.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/role")
public class RoleController {


    @Autowired
    RoleService roleService;

    /**
     * 查询角色列表
     *
     * @param roleDto
     * @return
     */
    @GetMapping("/listuser")
    public API<List<Role>> listUser(Role role) {
        List<Role> roles = roleService.queryByPage(role);
        return API.ok(roles);

    }


//    /**
//     * 删除角色
//     * @param requestBody
//     * @return
//     */
//    @PostMapping("/delete")
//    public String delete(@RequestBody String requestBody){
//        Map<String,Object> rquestMap = super.getRequestBody(requestBody);
//        roleService.delete(rquestMap);
//        return successResult();
//    }


    /**
     * 添加角色
     * @param requestBody
     * @return
     */
    @PostMapping("/add")
    public API add(@RequestBody Role role){
        roleService.add(role);
        return  API.ok();

    }

    /**
     * 修改角色
     * @param requestBody
     * @return
     */
    @PostMapping("/update")
    public API update(@RequestBody Role role){
        roleService.update(role);
        return  API.ok();
    }

}
