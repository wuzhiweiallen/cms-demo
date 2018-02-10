package com.ncs.demo.service;

import com.ncs.demo.po.BirthPerson;
import com.ncs.demo.query.UserQuery;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/4
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public interface BirthPersonService {

    /**
     * 根据条件查询user
     * @param userQuery
     * @return
     */
    List<BirthPerson> getUser(UserQuery userQuery);

    /**
     * 根据条件查询user的count
     * @param userQuery
     * @return
     */
    int selectUserCount(UserQuery userQuery);

    /**
     * 逻辑删除用户
     * @param userQuery
     */
    void logicDeleteUser(UserQuery userQuery);

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    BirthPerson getUserById(int userId);

    /**
     * 更新用户
     * @param birthPerson
     */
    void updateUser(BirthPerson birthPerson);

    /**
     * 插入user
     * @param birthPerson
     */
    void insertUser(BirthPerson birthPerson);

    List<BirthPerson> getAllBirthPerson();

}
