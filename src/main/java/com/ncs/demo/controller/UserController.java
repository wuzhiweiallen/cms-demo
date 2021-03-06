package com.ncs.demo.controller;

import com.ncs.demo.commons.BaseResponseVO;
import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.po.User;
import com.ncs.demo.query.UserQuery;
import com.ncs.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/7
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponseVO login(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpSession session){
        User user = null;
        /*User user = (User) session.getAttribute("user");
        if(user != null){
            return "index";
        }*/
        try{
            user = userService.login(username, password);
        } catch (Exception e) {
            /*return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());*/
        }
        if(user != null){
            session.setAttribute("user", user);
            session.setAttribute("name", user.getName());
            /*return "index";*/
            return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
        }

        /*return "login";*/
        return new BaseResponseVO(CommonConstants.FAIL_CODE, CommonConstants.LOGIN_FAIL_DESC);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        try {
            session.removeAttribute("user");
            session.removeAttribute("name");
        } catch (Exception e) {
        }

        return "redirect:login";
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public BaseResponseVO saveUser(@RequestBody User user){
        try{
            userService.insert(user);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.REGISTER_SUCCESS_DESC);
    }

    @RequestMapping("/modifyPassword")
    @ResponseBody
    public BaseResponseVO modifyPassword(@RequestParam(value = "password") String password, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return new BaseResponseVO(CommonConstants.NOT_LOGIN_CODE, CommonConstants.NOT_LOGIN);
        }
        UserQuery query = new UserQuery();
        query.setPassword(password);
        query.setUserId(user.getId());
        userService.modifyPassword(query);
        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

}
