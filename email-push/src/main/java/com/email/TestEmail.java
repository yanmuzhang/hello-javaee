package com.email;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @description: todo
 * @create: 2020-06-01 14:43
 */
public class TestEmail {
    public static void main(String[] a){
        EmailEntity email = new EmailEntity();
        email.setUserName("jaguar_zc@sina.com");
        email.setPassword("6d99bfa9109211a0");
        email.setHost("smtp.sina.com");
        email.setPort(25);
        email.setFromAddress("jaguar_zc@sina.com");
        email.setToAddress("zhangchaog@vip.qq.com");
        email.setSubject("这是一封测试邮件!!!!");
        email.setContext("看看这是什么");
        email.setContextType("text/html;charset=utf-8");
        boolean flag = EmailPush.EmailSendTest(email);
        System.err.println("邮件发送结果=="+flag);
    }

}
