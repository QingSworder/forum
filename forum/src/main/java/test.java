import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/16.
 */
@WebServlet("/test")
public class test extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwords("/include/test.jsp",req,resp);
    }
    /*
    public static void main(String[] args) {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(Config.get("email_smtp"));
        htmlEmail.setSmtpPort(Integer.valueOf(Config.get("email_port")));
        htmlEmail.setAuthentication(Config.get("email_username"),Config.get("email_password"));
        htmlEmail.setStartTLSEnabled(true);
        System.out.println(Config.get("email_smtp"));
        System.out.println(Config.get("email_port"));
        System.out.println(Config.get("email_username"));
        System.out.println(Config.get("email_password"));

        try {
            htmlEmail.setFrom(Config.get("email_frommail"));
            htmlEmail.setSubject("www");
            htmlEmail.setHtmlMsg("wwewe");
            htmlEmail.addTo("373053958@qq.com");

            htmlEmail.send();
        } catch (EmailException ex) {
            //logger.error("向{}发送邮件失败",toAddress);
            throw new RuntimeException("发送邮件失败:" );
        }

    }*/
}
