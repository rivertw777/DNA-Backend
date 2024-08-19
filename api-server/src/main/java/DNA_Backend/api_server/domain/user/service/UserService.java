package DNA_Backend.api_server.domain.user.service;

import static DNA_Backend.api_server.domain.user.exception.UserExceptionMessage.USER_NAME_NOT_FOUND;
import static DNA_Backend.api_server.domain.user.exception.UserExceptionMessage.USER_NOT_FOUND;

import DNA_Backend.api_server.domain.user.dto.EmailDto.SendEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.EmailDto.UserPopupStatusResponse;
import DNA_Backend.api_server.domain.user.dto.EmailDto.VerifyEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.EmailDto.VerifyEmailCodeResponse;
import DNA_Backend.api_server.domain.user.dto.UserDto.CheckDuplicateUsernameRequest;
import DNA_Backend.api_server.domain.user.dto.UserDto.CheckDuplicateUsernameResponse;
import DNA_Backend.api_server.domain.user.dto.UserDto.SignUpRequest;
import DNA_Backend.api_server.domain.user.dto.UserDto.UsernameResponse;
import DNA_Backend.api_server.domain.user.exception.UserException;
import DNA_Backend.api_server.domain.user.model.PopupStatus;
import DNA_Backend.api_server.domain.user.model.User;
import DNA_Backend.api_server.domain.user.repository.UserRepository;
import DNA_Backend.api_server.global.redis.service.RedisService;
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
    private final EmailVerificationService emailVerificationService;
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
    public void signUp(SignUpRequest requestParam) {
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        saveUser(requestParam, encodedPassword);
    }

    private void saveUser(SignUpRequest requestParam, String encodedPassword){
        User user = User.createUser(requestParam.username(), requestParam.email(), encodedPassword);
        userRepository.save(user);
    }

    // 이름 중복 여부 확인
    @Transactional(readOnly = true)
    public CheckDuplicateUsernameResponse CheckDuplicateUsername(CheckDuplicateUsernameRequest requestParam){
        boolean isDuplicate = userRepository.findByUsername(requestParam.username()).isPresent();

        return new CheckDuplicateUsernameResponse(isDuplicate);
    }

    // 사용자 이름 조회
    public UsernameResponse getUsername(String username) {
        return new UsernameResponse(username);
    }

    // 이메일 인증 코드 전송
    public void sendEmailCode(SendEmailCodeRequest reqeustParam) {
        String code = createCode();
        emailVerificationService.sendEmail(reqeustParam.email(), code);
        redisService.setWithExpiration(EMAIL_AUTH_CODE_PREFIX + reqeustParam.email(), code, authCodeExpirationMillis);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // 이메일 인증 코드 검증
    public VerifyEmailCodeResponse verifyEmailCode(VerifyEmailCodeRequest requestParam) {
        String findCode = redisService.get(EMAIL_AUTH_CODE_PREFIX + requestParam.email());
        boolean isVerified = requestParam.code().equals(findCode);

        return new VerifyEmailCodeResponse(isVerified);
    }

    // 사용자 팝업 상태 조회
    @Transactional(readOnly = true)
    public UserPopupStatusResponse getUserPopupStatus(Long userId) {
        User user = findUser(userId);

        return new UserPopupStatusResponse(user.getPopupStatus().getValue());
    }

    // 사용자 팝업 상태 변경
    @Transactional
    public void UpdateUserPopupStatus(Long userId) {
        User user = findUser(userId);

        user.setPopupStatus(PopupStatus.NONE);
    }

}
