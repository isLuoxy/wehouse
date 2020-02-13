package cn.l99.wehouse.mail;

/**
 * 学生认证的
 */
public class StudentCertificationMailTemplate implements MailTemplate {

    private String prefix = "请点击以下链接进行学生认证，认证成功后就可以享受学生免押金功能!(链接48小时内有效，如无法点击，请复制链接到浏览器访问) </br>";

    private String contentTemplate = "<a href= \"%s\"> </br>";
    private String suffix = "wehouse";

    @Override
    public String generatorMailContent(String link) {
        StringBuilder stringBuilder = new StringBuilder(prefix);
        stringBuilder.append(String.format(contentTemplate, link));
        stringBuilder.append(suffix);
        return stringBuilder.toString();
    }
}
