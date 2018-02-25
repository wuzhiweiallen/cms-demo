package com.ncs.demo.controller;

import com.ncs.demo.commons.BaseResponseVO;
import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.commons.ResponseVO;
import com.ncs.demo.po.AffairRemind;
import com.ncs.demo.po.User;
import com.ncs.demo.query.AffairRemindQuery;
import com.ncs.demo.service.AffairRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/13
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/affairRemind")
public class AffairRemindController {

    @Autowired
    private AffairRemindService affairRemindService;

    /**
     * 根据条件查询AffairRemind
     * @param subject
     * @param remindTime
     * @param page
     * @param size
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAffairRemindByCondition", method = {RequestMethod.GET})
    public ResponseVO getAffairRemindServiceByCondition(@RequestParam(value = "subject", required = false) String subject,
                                                @RequestParam(value = "remindTime", required = false) String remindTime,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "rows", defaultValue = "10") int size,
                                                HttpSession httpSession){
        AffairRemindQuery query = new AffairRemindQuery();
        query.setPage(page);
        query.setSize(size);
        query.setSubject(subject);
        query.setRemindTime(remindTime);
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            query.setUserId(user.getId());
        }
        int total = affairRemindService.selectAffairRemindCount(query);
        List<AffairRemind> affairReminds = affairRemindService.selectAffairRemind(query);

        return new ResponseVO(page, size, total, affairReminds);
    }

    /**
     * 删除affairRemind根据ID
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteAffairRemind", method = {RequestMethod.POST})
    public BaseResponseVO deleteAffairRemind(@RequestParam(value = "id") List<Integer> ids){
        AffairRemindQuery query = new AffairRemindQuery();
        query.setIds(ids);
        try{
            affairRemindService.deldeteAffairRemind(query);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 更新AffairRemind信息
     * @param affairRemind
     * @return
     */
    @RequestMapping(value = "/updateAffairRemind", method = {RequestMethod.POST})
    public BaseResponseVO updateAffairRemind(AffairRemind affairRemind){
        try{

            affairRemindService.updateAffairRemind(affairRemind);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 插入affairRemind
     * @param affairRemind
     * @return
     */
    @RequestMapping(value = "/saveAffairRemind", method = {RequestMethod.POST})
    public BaseResponseVO saveBirthPerson(AffairRemind affairRemind, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            affairRemind.setCreateUser(user.getId());
        } else {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, "请先登录");
        }
        try{
            affairRemindService.saveAffairRemind(affairRemind);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

}
