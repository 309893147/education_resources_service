package com.lss.education_resources_service.util;

import com.lss.education_resources_service.bean.entity.febs.MenuTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
//    public static <T> List<DeptTree<T>> buildDeptTree(List<DeptTree<T>> nodes) {
//        if (nodes == null) {
//            return null;
//        }
//        List<DeptTree<T>> result = new ArrayList<>();
//        nodes.forEach(children -> {
//            String pid = children.getParentId();
//            if (pid == null || "0".equals(pid)) {
//                result.add(children);
//                return;
//            }
//            for (DeptTree<T> n : nodes) {
//                String id = n.getId();
//                if (id != null && id.equals(pid)) {
//                    if (n.getChildren() == null)
//                        n.initChildren();
//                    n.getChildren().add(children);
//                    children.setHasParent(true);
//                    n.setHasChild(true);
//                    return;
//                }
//            }
//        });
//
//        return result;
//    }

    public static <T> MenuTree<T> buildMenuTree(List<MenuTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            Integer pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (MenuTree<T> parent : nodes) {
                Integer id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                    return;
                }
            }
        });

        MenuTree<T> root = new MenuTree<>();
        root.setId(0);
        root.setParentId(null);
        root.setHasParent(false);
        root.setHasChild(true);
        root.setChecked(true);
        root.setChilds(topNodes);
        Map<String, Object> state = new HashMap<>(16);
        root.setState(state);
        return root;
    }
}
