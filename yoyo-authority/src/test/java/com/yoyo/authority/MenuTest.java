package com.yoyo.authority;

import com.yoyo.authority.menu.dao.MenuRepository;
import com.yoyo.authority.menu.pojo.dto.MenuDTO;
import com.yoyo.framework.api.RTPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

/***
 @Author:MrHuang
 @Date: 2019/9/19 17:13
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {

    @Autowired
    private MenuRepository menuDao;

    @Test
    public void test() {
        RTPaging<MenuDTO> paging = menuDao.paging(Criteria.where("name").is("商品删除"), Sort.by(Sort.Direction.DESC, "ordered"), 1, 4);
        System.out.println(paging);
    }
}
