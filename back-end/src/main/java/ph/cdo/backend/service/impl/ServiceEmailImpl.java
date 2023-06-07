package ph.cdo.backend.service.impl;

import org.springframework.stereotype.Service;
import ph.cdo.backend.exceptions.EmailErrorException;
import ph.cdo.backend.service.EmailService;

@Service
public class ServiceEmailImpl implements EmailService {
    @Override
    public void sendEmailEnableAccount(String email) throws EmailErrorException {

    }

    @Override
    public void sendEmailForgotPassword(String email) throws EmailErrorException {

    }

    @Override
    public void sendEmail(String email, String subject, String body, String html) throws EmailErrorException {

    }
}
