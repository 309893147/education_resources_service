//package com.lss.education_resources_service.controller.rest.manager.user;
//
//import com.zhongchou.controller.BaseController;
//import com.zhongchou.dao.entity.user.DeptDto;
//import com.zhongchou.dao.entity.user.DeptTree;
//import com.zhongchou.service.user.DeptService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 部门相关累
// */
//@RestController
//@RequestMapping("/manager/dept")
//public class DeptController{
//
//    @Autowired
//    DeptService deptService;
//
//    /**
//     * @param requestBody
//     * @return
//     * @Log("新增部门")
//     */
//    @PostMapping("/add")
//    public String addDept(@RequestBody String requestBody) {
//        DeptDto deptDto = super.getRequestBody(requestBody, DeptDto.class);
//        deptService.save(deptDto);
//        return successResult();
//    }
//
//    /**
//     * @param requestBody
//     * @return
//     * @Log("修改部门")
//     */
//    @PostMapping("/update")
//    public String updateDept(@RequestBody String requestBody) {
//        DeptDto deptDto = super.getRequestBody(requestBody, DeptDto.class);
//        deptService.update(deptDto);
//        return successResult();
//    }
//
//    /**
//     * @param requestBody
//     * @return
//     * @Log("删除部门")
//     */
//    @PostMapping("/delete")
//    public String delete(@RequestBody String requestBody) {
//        Map<String, Object> requestMap = super.getRequestBody(requestBody);
//        deptService.delete(requestMap);
//        return successResult();
//    }
//
//    /**
//     * 查询部门树最外层
//     *
//     * @return
//     */
//    @GetMapping("/department")
//    public String getDeptTree() {
//        List<DeptTree<DeptDto>> deptDtos = deptService.getDeptTree();
//        return successResult(deptDtos);
//    }
//
//
//    /**
//     * @param deptName
//     * @return
//     */
//    @GetMapping("/sontree")
//    public String getSonTree(String deptName) {
//        List<DeptTree<DeptDto>> deptDtos = deptService.getSontree(deptName);
//        return successResult(deptDtos);
//    }
//}
