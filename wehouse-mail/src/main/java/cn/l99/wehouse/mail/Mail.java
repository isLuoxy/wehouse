package cn.l99.wehouse.mail;


import cn.l99.wehouse.mail.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


/**
 * 邮箱工具类
 */
@Component
public class Mail {


    @Autowired
    MailConfig mailConfig;

    @Autowired
    JavaMailSender javaMailSender;


    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    public boolean sendMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailConfig.getUsername());
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
