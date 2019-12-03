//package com.lss.education_resources_service.service.febs;
//
//import com.zhongchou.dao.entity.user.DeptDto;
//import com.zhongchou.dao.entity.user.DeptTree;
//import com.zhongchou.dao.mapper.user.DeptMapper;
//import com.zhongchou.exception.APIError;
//import com.zhongchou.util.SysUtils;
//import com.zhongchou.util.TreeUtil;
//import jodd.util.StringPool;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.util.*;
//
//@Service
//@Transactional
//public class DeptService {
//
//    @Autowired
//    DeptMapper deptMapper;
//
//
//    public void save(DeptDto deptDto) {
//        if ( StringUtils.isEmpty(deptDto.getDeptName())) {
//            APIError.CUSTOM.set(400, "参数必能为空").expose();
//        }
//        if (StringUtils.isEmpty(deptDto.getParentId())){
//            deptDto.setParentId("0");
//        }
//        deptDto.setDeptId(SysUtils.getSysUUID());
//        deptDto.setCreateDate(new Date());
//        if (StringUtils.isEmpty(deptDto.getOrderNum())) {
//            deptDto.setOrderNum(Long.valueOf("0"));
//        }
//        deptMapper.save(deptDto);
//    }
//
//    public void update(DeptDto deptDto) {
//        if (StringUtils.isEmpty(deptDto.getParentId()) || StringUtils.isEmpty(deptDto.getDeptName()) || StringUtils.isEmpty(deptDto.getDeptId())) {
//            APIError.CUSTOM.set(400, "参数必能为空").expose();
//        }
//        deptDto.setUpdateDate(new Date());
//        deptMapper.update(deptDto);
//    }
//
//    public void delete(Map<String, Object> requestMap) {
//        String deptIds = (String) requestMap.get("deptIds");
//        String[] split = deptIds.split(StringPool.COMMA);
//        for (String deptId : split) {
//            Set<String> deptSetIds = new HashSet<>();
//            deptSetIds.add(deptId);
//            findSubNode(deptSetIds,deptId);
//            deptMapper.delete(deptSetIds);
//        }
//    }
//
//
//    private void findSubNode(Set<String> deleteIds, String deptId) {
//        List<String> setDb = deptMapper.getSonDeptId(deptId);
//        for (String s : setDb) {
//            deleteIds.add(s);
//            findSubNode(deleteIds,s);
//        }
//    }
//
//
//    public List<DeptTree<DeptDto>> getDeptTree() {
//       List<DeptDto> list =  deptMapper.getDeptTree();
//       List<DeptTree<DeptDto>> trees = this.convertDepts(list);
//       return TreeUtil.buildDeptTree(trees);
//    }
//
//
//    private List<DeptTree<DeptDto>> convertDepts(List<DeptDto> depts){
//        List<DeptTree<DeptDto>> trees = new ArrayList<>();
//        depts.forEach(dept -> {
//            DeptTree<DeptDto> tree = new DeptTree<>();
//            tree.setId(String.valueOf(dept.getDeptId()));
//            tree.setParentId(String.valueOf(dept.getParentId()));
//            tree.setName(dept.getDeptName());
//            tree.setData(dept);
//            trees.add(tree);
//        });
//        return trees;
//    }
//
//    public List<DeptTree<DeptDto>> getSontree(String deptName) {
//        List<DeptDto> list =  deptMapper.getSontree(deptName);
//        List<DeptTree<DeptDto>> trees =  this.convertDepts(list);
//        return TreeUtil.buildDeptTree(trees);
//    }
//}
