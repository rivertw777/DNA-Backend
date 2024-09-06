package DNA_Backend.api_server.domain.user.service;

import static DNA_Backend.api_server.domain.user.message.UserExceptionMessage.USER_NAME_NOT_FOUND;
import static DNA_Backend.api_server.domain.user.message.UserExceptionMessage.USER_NOT_FOUND;

import DNA_Backend.api_server.domain.user.dto.request.CheckDuplicateUsernameRequest;
import DNA_Backend.api_server.domain.user.dto.request.SendEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.request.SignUpRequest;
import DNA_Backend.api_server.domain.user.dto.request.VerifyEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.response.CheckDuplicateUsernameResponse;
import DNA_Backend.api_server.domain.user.dto.response.UserPopupStatusResponse;
import DNA_Backend.api_server.domain.user.dto.response.UsernameResponse;
import DNA_Backend.api_server.domain.user.dto.response.VerifyEmailCodeResponse;
import DNA_Backend.api_server.domain.user.model.enums.PopupStatus;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.repository.UserRepository;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import DNA_Backend.api_server.global.redis.repository.RedisRepository;
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
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;

    // id로 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new DnaApplicationException(USER_NOT_FOUND.getValue()));
    }

    // 이름으로 조회
    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new DnaApplicationException(USER_NAME_NOT_FOUND.getValue()));
    }

    // PUBLIC - 회원 가입
    @Transactional
    public void signUp(SignUpRequest requestParam) {
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        saveUser(requestParam, encodedPassword);
    }

    private void saveUser(SignUpRequest requestParam, String encodedPassword){
        User user = User.createUser(requestParam.username(), requestParam.email(), encodedPassword);
        userRepository.save(user);
    }

    // PUBLIC - 이름 중복 여부 확인
    @Transactional(readOnly = true)
    public CheckDuplicateUsernameResponse CheckDuplicateUsername(CheckDuplicateUsernameRequest requestParam){
        boolean isDuplicate = userRepository.findByUsername(requestParam.username()).isPresent();
        return new CheckDuplicateUsernameResponse(isDuplicate);
    }

    // USER - 이름 조회
    public UsernameResponse getUsername(String username) {
        return new UsernameResponse(username);
    }

    // PUBLIC - 이메일 인증 코드 전송
    public void sendEmailCode(SendEmailCodeRequest reqeustParam) {
        String code = createCode();
        emailVerificationService.sendEmail(reqeustParam.email(), code);
        redisRepository.setWithExpiration(EMAIL_AUTH_CODE_PREFIX + reqeustParam.email(), code, authCodeExpirationMillis);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // PUBLIC - 이메일 인증 코드 검증
    public VerifyEmailCodeResponse verifyEmailCode(VerifyEmailCodeRequest requestParam) {
        String findCode = redisRepository.get(EMAIL_AUTH_CODE_PREFIX + requestParam.email());
        boolean isVerified = requestParam.code().equals(findCode);
        return new VerifyEmailCodeResponse(isVerified);
    }

    // USER - 팝업 상태 조회
    @Transactional(readOnly = true)
    public UserPopupStatusResponse getUserPopupStatus(Long userId) {
        User user = findUser(userId);
        return new UserPopupStatusResponse(user.getPopupStatus().getValue());
    }

    // USER - 팝업 상태 초기화
    @Transactional
    public void UpdateUserPopupStatus(Long userId) {
        User user = findUser(userId);
        user.setPopupStatus(PopupStatus.NONE);
    }

}
