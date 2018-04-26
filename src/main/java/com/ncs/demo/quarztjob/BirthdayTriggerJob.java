package com.ncs.demo.quarztjob;

import com.ncs.demo.commons.CalendarUtil;
import com.ncs.demo.commons.CommonConstants;
import com.ncs.demo.po.BirthPerson;
import com.ncs.demo.service.BirthPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/9
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BirthdayTriggerJob {

    private Logger logger = Logger.getLogger(BirthdayTriggerJob.class);

    @Autowired
    private BirthPersonService birthPersonService;

    private String smtpHost; // 邮件服务器地址
    private String sendUserName; // 发件人的用户名
    private String sendUserPass; // 发件人密码
    private MimeMessage mimeMsg; // 邮件对象
    private Session session;
    private Properties props;
    private Multipart mp;// 附件添加的组件
    private List<FileDataSource> files = new LinkedList<>();// 存放附件文件

    /*@Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void work() throws Exception {
        logger.info("执行调度任务：" + new Date());
    }

    @Scheduled(fixedRate = 5000)//每5秒执行一次
    public void play() throws Exception {
        logger.info("执行Quartz定时器任务：" + new Date());
    }*/

    /*一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。

    按顺序依次为
    秒（0~59）
    分钟（0~59）

    小时（0~23）

    天（月）（0~31，但是你需要考虑你月的天数）

    月（0~11）

    天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）

            7.年份（1970－2099）

    其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?.

    0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
    0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
    0 0 12 ? * WED 表示每个星期三中午12点
    "0 0 12 * * ?" 每天中午12点触发
    "0 15 10 ? * *" 每天上午10:15触发
    "0 15 10 * * ?" 每天上午10:15触发
    "0 15 10 * * ? *" 每天上午10:15触发*/
    @Scheduled(cron = "0 15 10 ? * *")
    public void work() throws Exception {
        List<BirthPerson> birthPersons = birthPersonService.getAllBirthPerson();
        if (birthPersons != null && birthPersons.size() > 0) {
            for (BirthPerson birthPerson : birthPersons) {
                //这是阴历的生日
                String birthday = birthPerson.getBirthday();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date nowDate = new Date();
                String nowDateString = sdf.format(nowDate);
                //阳历日期转换为阴历日期
                String lunar = CalendarUtil.solarToLunar(nowDateString);
                //比较月和日
                if (birthday.substring(5).equals(lunar.substring(5))) {
                    String emailBody = "来自于系统的提醒，今天是"+ birthPerson.getName() + "的生日！！！";
                    logger.info("今天是" + birthPerson.getName() + "的生日哦");
                    logger.info("==========sending email ==========");
                    BirthdayTriggerJob email = BirthdayTriggerJob.entity(CommonConstants.SMTP_HOST, CommonConstants.USER_NAME,
                    CommonConstants.PASSWORD, CommonConstants.RECEIVER, CommonConstants.SUBJECT, emailBody);
                    try {
                        email.send(); // 发送！
                        logger.info("==========send email successfully==========");
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("==========send email to unsuccessfully==========");
                    }
                }
            }
        }

    }

    //不写这个默认的构造器就要报错
    private BirthdayTriggerJob(){

    }

    /**
     * 发邮件前的初始化工作
     */
    private void init() {
        if (props == null) {
            props = System.getProperties();
        }
        //Security.addProvider(new Provider());
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true"); // 需要身份验证
        session = Session.getDefaultInstance(props, null);
        // 置true可以在控制台（console)上看到发送邮件的过程
        session.setDebug(true);
        // 用session对象来创建并初始化邮件对象
        mimeMsg = new MimeMessage(session);
        // 生成附件组件的实例
        mp = new MimeMultipart();
    }

    /**
     *
     * @param smtpHost
     * @param sendUserName
     * @param sendUserPass
     * @param to
     * @param mailSubject
     * @param mailBody
     */
    private BirthdayTriggerJob(String smtpHost, String sendUserName, String sendUserPass, String to, String mailSubject, String mailBody) {
        this.smtpHost = smtpHost;
        this.sendUserName = sendUserName;
        this.sendUserPass = sendUserPass;
        init();
        setFrom(sendUserName);
        setTo(to);
        setBody(mailBody);
        setSubject(mailSubject);
    }

    /**
     * 邮件实体
     *
     * @param smtpHost
     *            邮件服务器地址
     * @param sendUserName
     *            发件邮件地址
     * @param sendUserPass
     *            发件邮箱密码
     * @param to
     *            收件人，多个邮箱地址以半角逗号分隔
     * @param mailSubject
     *            邮件主题
     * @param mailBody
     *            邮件正文
     * @return
     */
    public static BirthdayTriggerJob entity(String smtpHost, String sendUserName, String sendUserPass, String to, String mailSubject, String mailBody) {
        return new BirthdayTriggerJob(smtpHost, sendUserName, sendUserPass, to, mailSubject, mailBody);
    }


    /**
     * 设置邮件主题
     *
     * @param mailSubject
     * @return
     */
    private boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8
     *
     * @param mailBody
     * @return
     */
    private boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
            // 在组件上添加邮件文本
            mp.addBodyPart(bp);
        } catch (Exception e) {
            logger.info("设置邮件正文时发生错误！" + e);
            return false;
        }
        return true;
    }

    /**
     * 设置发件人地址
     *
     * @param from
     *            发件人地址
     * @return
     */
    private boolean setFrom(String from) {
        try {
            mimeMsg.setFrom(new InternetAddress(from));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置收件人地址
     *
     * @param to 收件人的地址
     * @return
     */
    private boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置抄送
     *
     * @param cc
     * @return
     */
    private boolean setCC(String cc) {
        if (cc == null) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 发送邮件
     *
     * @return
     */
    public boolean send() throws Exception {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();
        Transport transport = session.getTransport("smtp");
        // 连接邮件服务器并进行身份验证
        transport.connect(smtpHost, sendUserName, sendUserPass);
        // 发送邮件
        transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
        logger.info("发送邮件成功！");
        transport.close();
        return true;
    }

}
