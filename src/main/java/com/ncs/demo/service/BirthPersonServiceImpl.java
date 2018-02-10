package com.ncs.demo.service;

import com.ncs.demo.dao.BirthPersonDao;
import com.ncs.demo.po.BirthPerson;
import com.ncs.demo.query.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/4
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BirthPersonServiceImpl implements BirthPersonService {

    @Autowired
    private BirthPersonDao birthPersonDao;

    @Override
    public List<BirthPerson> getUser(UserQuery userQuery) {
        int page = userQuery.getPage();
        int size = userQuery.getSize();
        String name = userQuery.getName();
        String birthday = userQuery.getBirthday();
        int userId = userQuery.getUserId();
        Map map = new HashMap();
        map.put("name", name);
        map.put("birthday", birthday);
        map.put("userId", userId);
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        return birthPersonDao.selectUser(map, rowBounds);
    }

    @Override
    public int selectUserCount(UserQuery userQuery) {
        String name = userQuery.getName();
        String birthday = userQuery.getBirthday();
        int userId = userQuery.getUserId();
        Map map = new HashMap();
        map.put("name", name);
        map.put("name", name);
        map.put("birthday", birthday);
        map.put("userId", userId);

        return birthPersonDao.selectUserCount(map);
    }

    @Override
    public void logicDeleteUser(UserQuery userQuery) {
        List<Integer> userId = userQuery.getUserIds();
        for(Integer id : userId){
            BirthPerson birthPerson = birthPersonDao.selectUserById(id);
            birthPersonDao.logicDeleteUser(birthPerson);
        }
    }

    @Override
    public BirthPerson getUserById(int userId) {

        return birthPersonDao.selectUserById(userId);
    }

    @Override
    public void updateUser(BirthPerson birthPerson) {
        if(birthPerson != null){
            int id = birthPerson.getId();
            BirthPerson birthPersonPO = birthPersonDao.selectUserById(id);
            if(birthPersonPO != null){
                birthPersonPO.setName(birthPerson.getName());
                birthPersonPO.setRelation(birthPerson.getRelation());
                birthPersonPO.setBirthday(birthPerson.getBirthday());
                birthPersonPO.setAddress(birthPerson.getAddress());
                birthPersonPO.setGender(birthPerson.getGender());
                birthPersonPO.setEmail(birthPerson.getEmail());
                birthPersonPO.setAge(birthPerson.getAge());
                birthPersonDao.updateUser(birthPersonPO);
            }
        }
    }

    @Override
    public void insertUser(BirthPerson birthPerson) {
        if(birthPerson !=null){
            birthPersonDao.insertUser(birthPerson);
        }
    }

    @Override
    public List<BirthPerson> getAllBirthPerson(){

        return birthPersonDao.selectAllBirthPerson();
    }
}
