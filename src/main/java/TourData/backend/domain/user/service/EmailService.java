package TourData.backend.domain.user.service;

import static TourData.backend.domain.user.exception.EmailExceptionMessage.SEND_MAIL_FAILED;

import TourData.backend.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private static final String TITLE = "[DNA] Email Verification Code";
    private static final String TEXT_PREFIX = "Please copy and enter the email verification code listed below.\nVerification Code: ";

    public void sendEmail(String email, String code) {
        String text = TEXT_PREFIX + code;
        SimpleMailMessage emailForm = createEmailForm(email, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new UserException(SEND_MAIL_FAILED.getMessage());
        }
    }

    private SimpleMailMessage createEmailForm(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(TITLE);
        message.setText(text);
        return message;
    }
}