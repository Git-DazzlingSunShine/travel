package com.itheima.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.ResourceBundle;

public class MailUtil {
    private static String hostname;
    private static String authname;
    private static String authpass;
    private static String charset;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("mail");
        hostname = bundle.getString("host.name");
        authname = bundle.getString("authen.name");
        authpass = bundle.getString("authen.pwd");
        charset = bundle.getString("charset");
    }

    public static void sendEmail(String emailTo, String recipient, String msg) throws EmailException {

        //1、创建一个HtmlEmail对象
        HtmlEmail htmlEmail = new HtmlEmail();
        //2、设置邮箱服务器的参数
        htmlEmail.setHostName(hostname);
        //设置编码-
        htmlEmail.setCharset(charset);

        //3、设置邮箱验证（客户端授权）
        //username：邮箱账号
        //password：客户端授权码
        htmlEmail.setAuthentication(authname, authpass);

        //4、设置收件人 和 发件人
        htmlEmail.setFrom(authname, "黑马旅游");
        htmlEmail.addTo(emailTo, recipient);

        //5、设置主题 和  正文
        htmlEmail.setSubject("黑马旅游注册激活邮件");
        htmlEmail.setHtmlMsg(msg);

        //6、发送邮件
        htmlEmail.send();
    }
}
