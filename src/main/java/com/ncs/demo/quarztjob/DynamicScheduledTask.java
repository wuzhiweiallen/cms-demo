package com.ncs.demo.quarztjob;

import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.dao.AffairRemindDao;
import com.ncs.demo.po.AffairRemind;
import com.ncs.demo.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/12
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DynamicScheduledTask implements SchedulingConfigurer {

    private Logger logger = Logger.getLogger(DynamicScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static String DEFAULT_CRON = "0 30,09 10,15 * * ? ";

    @Autowired
    private AffairRemindDao affairRemindDao;

    /*public DynamicScheduledTask() {
        // 每天上午十点和下午四点执行
        cron = "0 0 10,16 * * ?";
        new Thread(new Runnable() {
            // 开启新线程模拟外部更改了任务执行周期.
            @Override
            public void run() {

                *//*try {
                    // 让线程睡眠 15秒.
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //修改为：每10秒执行一次.
                cron = "0/10 * * * * *";
                System.err.println("cron change to:"+cron);*//*
            }
        }).start();

    }*/

    /*@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // 定时任务的业务逻辑
                logger.info(dateFormat.format(new Date()));
            }
        }, triggerContext -> {
            // 定时任务触发，可修改定时任务的执行周期
            CronTrigger trigger = new CronTrigger(cron);
            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        });
    }*/

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<AffairRemind> affairReminds = affairRemindDao.selectAllAffairRemind();
        if(affairReminds != null && affairReminds.size() > 0){
            //循环开启定时任务
            affairReminds.forEach((affairRemind) -> {
                String cron = affairRemind.getCron();
                if (StringUtils.isNotBlank(cron)) {
                    taskRegistrar.addTriggerTask(() -> {
                        // 定时任务的业务逻辑
                        logger.info("custom timer: " + affairRemind.getContent() + "cron: "+cron);
                    }, triggerContext -> {
                        // 定时任务触发，可修改定时任务的执行周期
                        CronTrigger trigger = new CronTrigger(cron);
                        Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                        return nextExecDate;
                    });
                } else {
                    String cronDefault = DEFAULT_CRON;
                    String nowDateString = DateUtils.getCurrentUnixTimeStamp();
                    taskRegistrar.addTriggerTask(() -> {
                        logger.info("default timer: " + affairRemind.getContent() + ", cron: " + cronDefault);
                        if(nowDateString.equals(affairRemind.getRemindTime())){
                            // 定时任务的业务逻辑
                            String emailBody = affairRemind.getContent();
                            logger.info("==========sending email ==========");
                            BirthdayTriggerJob email = BirthdayTriggerJob.entity(CommonConstants.SMTP_HOST, CommonConstants.USER_NAME,
                                    CommonConstants.PASSWORD, CommonConstants.RECEIVER, affairRemind.getSubject(), emailBody);
                            try {
                                email.send(); // 发送！
                                logger.info("==========send email successfully==========");
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.info("==========send email to unsuccessfully==========");
                            }
                        }
                    }, triggerContext -> {
                        // 定时任务触发，可修改定时任务的执行周期
                        CronTrigger trigger = new CronTrigger(cronDefault);
                        Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                        return nextExecDate;
                    });
                }
            });

        }
    }
    /*public void setCron(String cron) {
        this.cron = cron;
    }*/
}
