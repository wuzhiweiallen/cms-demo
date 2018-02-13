package com.ncs.demo.service;

import com.ncs.demo.dao.UserDao;
import com.ncs.demo.po.User;
import com.ncs.demo.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/7
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public void insert(User user) {

        userDao.insert(user);
    }

    @Override
    public void modifyPassword(UserQuery query) {
        String password = query.getPassword();
        int id = query.getUserId();
        User user = userDao.selectByPrimaryKey(id);
        if(user != null){
            user.setPassword(password);
        }
        userDao.updateByPrimaryKey(user);
    }
}
