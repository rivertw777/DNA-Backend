package TourData.backend.domain.user.service;

import static TourData.backend.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UsernameResponse;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameResponse;
import TourData.backend.domain.user.exception.UserException;
import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.model.Role;
import TourData.backend.domain.user.repository.UserRepository;
import TourData.backend.global.redis.service.RedisService;
import java.util.Collections;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final EmailService emailService;
    private final RedisService redisService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_AUTH_CODE_PREFIX = "Email Auth Code: ";

    // 회원가입
    @Transactional
    public void signUp(UserSignUpRequest requestParam) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        // User 엔티티 저장
        saveUser(requestParam, encodedPassword);
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

    // 이름 중복 체크
    @Transactional(readOnly = true)
    public ValidateDuplicateUsernameResponse validateDuplicateUserName(ValidateDuplicateUsernameRequest requestParam){
        boolean isDuplicated = userRepository.findByUsername(requestParam.username()).isPresent();
        return new ValidateDuplicateUsernameResponse(isDuplicated);
    }

    // 이름으로 조회
    @Transactional(readOnly = true)
    public User findUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UserException(USER_NAME_NOT_FOUND.getMessage()));
    }

    // 사용자 이름 조회
    public UsernameResponse getUserName(String username) {
        return new UsernameResponse(username);
    }

    // 이메일 인증 코드 전송
    public void sendCode(SendCodeRequest reqeustParam) {
        String code = createCode();
        emailService.sendEmail(reqeustParam.email(), code);
        redisService.saveWithExpiration(EMAIL_AUTH_CODE_PREFIX + reqeustParam.email(), code, authCodeExpirationMillis);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // 이메일 인증 코드 검증
    public EmailVerificationResponse verifyCode(VerifyCodeRequest requestParam) {
        String findCode = (String) redisService.get(EMAIL_AUTH_CODE_PREFIX + requestParam.email());
        boolean isVerified = requestParam.code().equals(findCode);
        return new EmailVerificationResponse(isVerified);
    }

}
