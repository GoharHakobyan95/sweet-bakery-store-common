package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AboutUsService {

    private final JavaMailSender mailSender;
    private final UserService userService;
    @Value("${spring.mail.username}")
    private String appEmail;


    //Receive emails from customers and resend  to admin email
    @Async
    public void sendMail(User user, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        userService.getAdminEmail().ifPresent(admin -> {
            mailMessage.setTo(admin.getEmail());
        });
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        mailMessage.setFrom(appEmail);

        String mailSubject = name + " has sent a message";
        StringBuilder sb = new StringBuilder();
        sb.append("Sender name ")
                .append(name).append("\n")
                .append("Sender phone ").append(phone).append("\n")
                .append("Sender email ")
                .append(email).append("\n")
                .append("Content ")
                .append(message).append("\n");

        mailMessage.setSubject(mailSubject);
        mailMessage.setText(sb.toString());
        mailSender.send(mailMessage);
    }
}

