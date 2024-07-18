package TourData.backend.domain.user.service;

import static TourData.backend.domain.user.exception.UserExceptionMessage.DUPLICATE_NAME;
import static TourData.backend.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UserNameResponse;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.exception.UserException;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.model.Role;
import TourData.backend.domain.user.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserSerivce {

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final EmailService emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public void signUp(UserSignUpRequest requestParam) {
        // 이름 중복 검증
        validateDuplicateName(requestParam.username());
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        // User 엔티티 저장
        saveUser(requestParam, encodedPassword);
    }

    private void validateDuplicateName(String username){
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new UserException(DUPLICATE_NAME.getMessage());
        }
    }

    private void saveUser(UserSignUpRequest requestParam, String encodedPassword){
        User user = User.builder()
                .username(requestParam.username())
                .email(requestParam.email())
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .build();
        userRepository.save(user);
    }

    // 이름으로 조회
    @Override
    @Transactional(readOnly = true)
    public User findUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UserException(USER_NAME_NOT_FOUND.getMessage()));
    }

    @Override
    public UserNameResponse getUserName(String username) {
        return new UserNameResponse(username);
    }

    @Override
    public void sendCode(SendCodeRequest reqeustParam) {
        String title = "[DNA] Email Verification Code";
        String code = createCode();
        String text = "code: " + code;
        emailService.sendEmail(reqeustParam.email(), title, text);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    @Override
    public EmailVerificationResponse verifyCode(VerifyCodeRequest reqeustParam) {
        return null;
    }

}
