package com.ncs.demo.controller;

import com.ncs.demo.commons.BaseResponseVO;
import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.commons.ResponseVO;
import com.ncs.demo.po.MoneyGift;
import com.ncs.demo.po.User;
import com.ncs.demo.query.MoneyGiftQuery;
import com.ncs.demo.service.MoneyGiftService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/11
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/moneyGift")
public class MoneyGiftController {

    private Logger logger = Logger.getLogger(MoneyGiftController.class);

    @Autowired
    private MoneyGiftService moneyGiftService;

    @GetMapping("/getMoneyGiftByCondition")
    public BaseResponseVO getMoneyGifyByCondition(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "givenDate", required = false) String givenDate,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "rows", defaultValue = "10") int size,
                                                  HttpSession session){
        int total;
        List<MoneyGift> moneyGifts;
        MoneyGiftQuery query = new MoneyGiftQuery();
        query.setGivenDate(givenDate);
        query.setName(name);
        query.setPage(page);
        query.setSize(size);
        User user = (User) session.getAttribute("user");
        if(user != null){
            query.setUserId(user.getId());
        } else {
            return new BaseResponseVO(CommonConstants.NOT_LOGIN_CODE, CommonConstants.NOT_LOGIN);
        }
        try{
            total = moneyGiftService.getMoneyGiftCount(query);
            moneyGifts = moneyGiftService.getMoneyGift(query);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new ResponseVO(page, size, total, moneyGifts);
    }

    @PostMapping("/deleteMoneyGift")
    public BaseResponseVO deleteMoneyGify(@RequestParam(value = "id") List<Integer> ids){

        MoneyGiftQuery query = new MoneyGiftQuery();
        query.setIds(ids);
        try{
            moneyGiftService.batchLogicDelete(ids);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 更新用户信息
     * @param moneyGift
     * @return
     */
    @RequestMapping(value = "/updateMoneyGift", method = {RequestMethod.POST})
    public BaseResponseVO updateMoneyGift(MoneyGift moneyGift){
        try{
            moneyGiftService.updateByPrimaryKey(moneyGift);
        } catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

    /**
     * 插入MoneyGift
     * @param moneyGift
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/saveMoneyGift", method = {RequestMethod.POST})
    public BaseResponseVO saveMoneyGift(MoneyGift moneyGift, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            moneyGift.setUserId(user.getId());
        } else {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, "请先登录");
        }
        /*try{*/
            moneyGiftService.insert(moneyGift);
        /*} catch (Exception e) {
            return new BaseResponseVO(CommonConstants.FAIL_CODE, e.getMessage());
        }*/

        return new BaseResponseVO(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_DESC);
    }

}
