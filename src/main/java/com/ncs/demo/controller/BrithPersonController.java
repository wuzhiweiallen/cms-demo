package com.ncs.demo.controller;

import com.ncs.demo.commons.BaseResponseVO;
import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.commons.ResponseVO;
import com.ncs.demo.po.BirthPerson;
import com.ncs.demo.po.User;
import com.ncs.demo.query.UserQuery;
import com.ncs.demo.service.BirthPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/4
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/birthPerson")
public class BrithPersonController {

    @Autowired
    private BirthPersonService birthPersonService;

    /**
     * 根据条件查询用户
     * @param name
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/getBirthPersonByCondition", method = {RequestMethod.GET})
    public ResponseVO getBirthPersonByCondition(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "birthday", required = false) String birthday,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "rows", defaultValue = "10") int size,
                              HttpSession httpSession){
        UserQuery userQuery = new UserQuery();
        userQuery.setPage(page);
        userQuery.setSize(size);
        userQuery.setName(name);
        userQuery.setBirthday(birthday);
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            userQuery.setUserId(user.getId());
        }
        int total = birthPersonService.selectUserCount(userQuery);
        List<BirthPerson> persons = birthPersonService.getUser(userQuery);

        return new ResponseVO(page, size, total, persons);
    }

    /**
     * 删除用户根据用户ID
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBirthPerson", method = {RequestMethod.POST})
    public BaseResponseVO deleteBirthPerson(@RequestParam(value = "id") List<Integer> ids){
        UserQuery userQuery = new UserQuery();
        userQuery.setUserIds(ids);
        try{
            birthPersonService.logicDeleteUser(userQuery);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 更新用户信息
     * @param birthPerson
     * @return
     */
    @RequestMapping(value = "/updateBirthPerson", method = {RequestMethod.POST})
    public BaseResponseVO updateBirthPerson(BirthPerson birthPerson){
        try{

            birthPersonService.updateUser(birthPerson);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 插入birthPerson
     * @param birthPerson
     * @return
     */
    @RequestMapping(value = "/saveBirthPerson", method = {RequestMethod.POST})
    public BaseResponseVO saveBirthPerson(BirthPerson birthPerson, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            birthPerson.setUserId(user.getId());
        } else {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, "请先登录");
        }
        try{
            birthPersonService.insertUser(birthPerson);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

}
