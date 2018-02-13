package com.ncs.demo.service;

import com.ncs.demo.po.User;
import com.ncs.demo.query.UserQuery;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/7
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    User login(String username, String password);

    void insert(User user);

    void modifyPassword(UserQuery query);
}
