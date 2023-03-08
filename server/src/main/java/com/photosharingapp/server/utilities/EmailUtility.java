package com.photosharingapp.server.utilities;

import com.photosharingapp.server.models.AppUser;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmailUtility {
    @Autowired
    private Environment environment;

    @Autowired
    private TemplateEngine templateEngine;

    public MimeMessagePreparator constructNewUserEmail(AppUser appUser, String password) {
        Context context = new Context();
        context.setVariable("user", appUser);
        context.setVariable("password", password);
        String text = templateEngine.process("newUserEmailTemplate", context);

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(appUser.getEmail());
                email.setSubject("Welcome to Photo Sharing App");
                email.setText(text, true);
                email.setFrom(String.valueOf(new InternetAddress(environment.getProperty("support.email"))));
            }
        };
        return mimeMessagePreparator;
    }

    public MimeMessagePreparator constructUpdatePasswordEmail(AppUser appUser, String password) {
        Context context = new Context();
        context.setVariable("user", appUser);
        context.setVariable("password", password);
        String text = templateEngine.process("updatePasswordEmailTemplate", context);

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(appUser.getEmail());
                email.setSubject("Welcome to Photo Sharing App");
                email.setText(text, true);
                email.setFrom(String.valueOf(new InternetAddress(environment.getProperty("support.email"))));
            }
        };
        return mimeMessagePreparator;
    }

    public MimeMessagePreparator constructResetPasswordEmail(AppUser appUser, String password) {
        Context context = new Context();
        context.setVariable("user", appUser);
        context.setVariable("password", password);
        String text = templateEngine.process("updatePasswordEmailTemplate", context);

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(appUser.getEmail());
                email.setSubject("Welcome to Photo Sharing App");
                email.setText(text, true);
                email.setFrom(String.valueOf(new InternetAddress(environment.getProperty("support.email"))));
            }
        };
        return mimeMessagePreparator;
    }
}