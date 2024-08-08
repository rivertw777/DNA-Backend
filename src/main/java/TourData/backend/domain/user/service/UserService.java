package TourData.backend.domain.user.service;

import static TourData.backend.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;
import static TourData.backend.domain.user.exception.UserExceptionMessage.USER_NOT_FOUND;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UsernameResponse;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameResponse;
import TourData.backend.domain.user.exception.UserException;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.repository.UserRepository;
import TourData.backend.global.redis.service.RedisService;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String EMAIL_AUTH_CODE_PREFIX = "Email Auth Code: ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    // id로 조회
    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new UserException(USER_NOT_FOUND.getMessage()));
    }

    // 이름으로 조회
    @Transactional(readOnly = true)
    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UserException(USER_NAME_NOT_FOUND.getMessage()));
    }

    // 회원 가입
    @Transactional
    public void signUp(UserSignUpRequest requestParam) {
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        saveUser(requestParam, encodedPassword);
    }

    private void saveUser(UserSignUpRequest requestParam, String encodedPassword){
        User user = User.createUser(requestParam, encodedPassword);
        userRepository.save(user);
    }

    // 이름 중복 체크
    @Transactional(readOnly = true)
    public ValidateDuplicateUsernameResponse validateDuplicateUserName(ValidateDuplicateUsernameRequest requestParam){
        boolean isDuplicated = userRepository.findByUsername(requestParam.username()).isPresent();
        return new ValidateDuplicateUsernameResponse(isDuplicated);
    }

    // 사용자 이름 조회
    public UsernameResponse getUserName(String username) {
        return new UsernameResponse(username);
    }

    // 이메일 인증 코드 전송
    public void sendCode(SendCodeRequest reqeustParam) {
        String code = createCode();
        emailService.sendEmail(reqeustParam.email(), code);
        redisService.setWithExpiration(EMAIL_AUTH_CODE_PREFIX + reqeustParam.email(), code, authCodeExpirationMillis);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // 이메일 인증 코드 검증
    public EmailVerificationResponse verifyCode(VerifyCodeRequest requestParam) {
        String findCode = redisService.get(EMAIL_AUTH_CODE_PREFIX + requestParam.email());
        boolean isVerified = requestParam.code().equals(findCode);
        return new EmailVerificationResponse(isVerified);
    }

}
