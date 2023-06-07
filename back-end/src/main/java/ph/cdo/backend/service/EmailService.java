package ph.cdo.backend.service;

import ph.cdo.backend.exceptions.EmailErrorException;

public interface EmailService {

    void sendEmailEnableAccount(String email) throws EmailErrorException;

    void sendEmailForgotPassword(String email) throws EmailErrorException;

    void sendEmail(String email, String subject, String body, String html) throws EmailErrorException;


}
