package ph.cdo.backend.service;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.exceptions.EmailErrorException;

public interface EmailService {

    void sendEmailEnableAccount(User user) throws MessagingException;

    void sendEmailForgotPassword(String email) throws EmailErrorException;

    void sendEmail(String email, String subject, Context context, String html) throws MessagingException;


}
