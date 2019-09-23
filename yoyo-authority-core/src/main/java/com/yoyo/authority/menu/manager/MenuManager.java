package com.yoyo.authority.menu.manager;

import com.yoyo.authority.menu.pojo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/23 11:13
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class MenuManager {

    /**
     * 递归设置菜单树
     * @param menuVOS
     * @param parentId
     * @return
     */
    public static List<MenuVO> loadTree(List<MenuVO> menuVOS, String parentId) {
        List<MenuVO> collect = menuVOS.stream().filter(s -> StringUtils.equals(parentId, s.getParentId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            for (MenuVO menuVO : collect) {
                List<MenuVO> childMenuVO = loadTree(menuVOS, menuVO.getMid());
                menuVO.setChildMenuVO(childMenuVO);
            }
        }
        return collect;
    }
}
