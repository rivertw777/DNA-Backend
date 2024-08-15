package DNA_Backend.api_server.domain.user.service;

import DNA_Backend.api_server.global.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final String SUBJECT = "[DNA] Email Verification Code";
    private static final String TEXT_PREFIX = "Please copy and enter the email verification code listed below.\nVerification Code: ";

    private final EmailService emailService;

    public void sendEmail(String email, String code){
        String subject = SUBJECT;
        String text = TEXT_PREFIX + code;
        emailService.sendEmail(email, subject, text);
    }

}