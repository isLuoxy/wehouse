package cn.l99.wehouse.mail;

/**
 * 邮件模版类
 *
 * @author L99
 */
public interface MailTemplate {

    /**
     * 通过动态的邮件内容构造整个邮件总内容
     *
     * @param link 动态变化的邮件内容
     * @return
     */
    String generatorMailContent(String link);
}
