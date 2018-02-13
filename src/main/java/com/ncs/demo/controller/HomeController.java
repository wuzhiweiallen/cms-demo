package com.ncs.demo.controller;

import com.ncs.demo.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/10
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "{page_name}")
    public String getHome(@PathVariable("page_name") String pageName,
                          HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return "login";
        }
        if(pageName.equals("jumpToIndexPage")){
            return "index";
        }
        httpSession.setAttribute("username", user.getName());

        return pageName;
    }

    @RequestMapping(value = "/birthdayManagement/{page_name}", method = {RequestMethod.GET})
    public String getbirthdayManagementHome(@PathVariable("page_name") String pageName,
                                            HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return "login";
        }
        httpSession.setAttribute("username", user.getName());

        return "/birthdayManagement/"+pageName;
    }

    @RequestMapping(value = "/moneyGiftManagement/{page_name}", method = {RequestMethod.GET})
    public String getMoneyGiftManagementHome(@PathVariable("page_name") String pageName,
                                            HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return "login";
        }
        httpSession.setAttribute("username", user.getName());

        return "/moneyGiftManagement/"+pageName;
    }

    @RequestMapping(value = "/affairRemindManagement/{page_name}", method = {RequestMethod.GET})
    public String getAffairRemindManagementHome(@PathVariable("page_name") String pageName,
                                             HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return "login";
        }
        httpSession.setAttribute("username", user.getName());

        return "/affairRemindManagement/"+pageName;
    }

}
