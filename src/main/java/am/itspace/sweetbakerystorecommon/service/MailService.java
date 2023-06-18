package am.itspace.sweetbakerystorecommon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    //Send link in email, for activate an account.
    @Async
    public void sendHtmlMail(String to, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,false);
            helper.setFrom("sweetbakerystore2022@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
