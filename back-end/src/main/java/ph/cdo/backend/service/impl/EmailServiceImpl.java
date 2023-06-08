package ph.cdo.backend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.TokenType;
import ph.cdo.backend.exceptions.EmailErrorException;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.repository.noBean.UserRepository;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.TokenService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private final TokenService tokenService;





    @Value("${app.config.HOST}")
    private String HOST;

    @Value("${app.config.SERVER_PORT}")
    private String SERVER_PORT;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, TokenService tokenService) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.tokenService = tokenService;
     ;
    }

    @Override
    public void sendEmailEnableAccount(User user) throws MessagingException {

        TokenDTO tokenDTO = tokenService.tokenBuilder(user, TokenType.Enabled_Account, 1,0,0);


        String userType = user.getClass().getSimpleName().toLowerCase().trim();
        final String confirmationLink = String.format("https://%s:%s/api/v1/%s/confirm/%s",
                HOST,
                SERVER_PORT,
                userType,
                tokenDTO.token()
                );

        final String subject = "Email Confirmation";
        final String html = "enabled_account";

        Context context = new Context();
        context.setVariable("confirmationLink", confirmationLink);
        context.setVariable("expirationDate", tokenDTO.expiryDate());

        sendEmail(user.getEmail(),subject,context,html);



    }

    @Override
    public void sendEmailForgotPassword(String email) throws EmailErrorException {

    }

    @Override
    public void sendEmail(String email, String subject, Context context, String html) throws MessagingException {



        String emailContent = templateEngine.process(html, context);
        // Set the email subject, recipient, and content
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setTo(email);
        helper.setText(emailContent, true);

        // Send the email
        mailSender.send(message);
    }
}
