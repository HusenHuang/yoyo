package com.yoyo.authority;

import com.mongodb.client.result.UpdateResult;
import com.yoyo.authority.role.dao.RoleRepository;
import com.yoyo.authority.role.pojo.dto.RoleDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/***
 @Author:MrHuang
 @Date: 2019/9/19 17:26
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Autowired
    private RoleRepository roleDao;


    /**
     * {
     *     "_id": "5d70e1432914431668c9d8211",
     *     "createTime": "2019-09-05 18:19:47",
     *     "name": "管理员",
     *     "remark": "管理员Admin",
     *     "roleStatus": 0,
     *     "updateTime": "2019-09-05 18:19:47",
     *     "version": 4,
     *     "_class": "com.yoyo.authority.role.pojo.dto.RoleDTO",
     *     "bindMenuId": [
     *         "5d7202222914431ff47652f5",
     *         "5d72028e2914431ff47652f71",
     *         "5d7204a32914431ff47652f9",
     *         "5d7204a92914431ff47652fa",
     *         "5d7204b02914431ff47652fb",
     *         "5d7205002914431ff47652fc",
     *         "5d72050e2914431ff47652fd",
     *         "5d7205152914431ff47652fe",
     *         "5d72051d2914431ff47652ff",
     *         "5d7205242914431ff4765300",
     *         "5d7629272914433ee0e0c601",
     *         "5d7629272914433ee0e0c601s"
     *     ],
     *     "ak": [
     *         {
     *             "a": "123456",
     *             "b": 876
     *         },
     *         {
     *             "a": "123457",
     *             "b": 877
     *         },
     *         {
     *             "a": "123458",
     *             "b": 878
     *         },
     *         {
     *             "a": "123459",
     *             "b": 879
     *         }
     *     ]
     * }
     * 查询集合中的对象
     * db.t_role.find({bindMenuId:{$elemMatch:{$eq:"5d72028e2914431ff47652f71"}}})
     * 或者
     * db.t_role.find({bindMenuId:{$eq:"5d72028e2914431ff47652f71"}})
     *
     * db.t_role.find({ak:{$elemMatch:{a:{$eq:"123459"}}}})
     *
     *
     * 往数组push数据
     *
     * 不去重
     * db.t_role.update({"_id": ObjectId("5d70e1432914431668c9d8211")}, {$push:{"bindMenuId":"123456"}})
     *
     * 去重
     * db.t_role.update({"_id": ObjectId("5d70e1432914431668c9d8211")}, {$addToSet:{"bindMenuId":"123456"}})
     *
     * 不去重
     * db.t_role.update({"remark": "管理员Admin"}, {$push:{ak:{$each:[{"a":"777","b":999}]}}})
     *
     * 去重
     * db.t_role.update({"remark": "管理员Admin"}, {$addToSet:{ak:{$each:[{"a":"777","b":999}]}}})
     */
    @Test
    public void test() {
        List<RoleDTO> roleDTOS = roleDao.find(Criteria.where("bindMenuId").elemMatch(Criteria.where("$eq").is("5d72028e2914431ff47652f71")));
        System.out.println(roleDTOS);


//        db.t_role.update({"remark": "管理员Admin"}, {$push:{ak:{$each:[{"a":"777","b":999}]}}})
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("a","7777");
        map.put("b", 888);
        Update update = new Update();
        update.push("ak", map);
        UpdateResult updateResult = roleDao.updateFirst(Criteria.where("remark").is("管理员Admin"), update);
        System.out.println(updateResult);

    }
}
