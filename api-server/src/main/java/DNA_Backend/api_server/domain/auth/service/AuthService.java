package DNA_Backend.api_server.domain.auth.service;

import static DNA_Backend.api_server.domain.auth.message.AuthExceptionMessage.PASSWORD_NOT_MATCH;

import DNA_Backend.api_server.domain.auth.dto.request.LoginRequest;
import DNA_Backend.api_server.domain.auth.dto.response.CheckFirstSocialLoginResponse;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.exception.DnaApplicationException;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.global.security.auth.UserDetailsServiceCustom;
import DNA_Backend.api_server.global.security.jwt.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final UserService userService;
    private final TokenManager tokenManager;
    private final PasswordEncoder passwordEncoder;

    // PUBLIC - 로그인
    @Transactional(readOnly = true)
    public String login(LoginRequest requestParam) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(requestParam.username());
        User user = userDetails.getUser();
        validatePassword(requestParam.password(), user.getPassword());
        String token = tokenManager.generateToken(userDetails);
        return token;
    }

    // 비밀번호 검증
    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new DnaApplicationException(PASSWORD_NOT_MATCH.getValue());
        }
    }

    // USER - 소셜 계정 최초 로그인 확인
    public CheckFirstSocialLoginResponse checkFirstSocialLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");
        return new CheckFirstSocialLoginResponse(isFirstLogin);
    }

    // USER - 새 이름 입력
    @Transactional
    public void updateUsername(String username, String newUsername) {
        User user = userService.findUser(username);
        user.setUsername(newUsername);
    }

    // 토큰 얻기
    @Transactional(readOnly = true)
    public String getToken(String newUsername) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(newUsername);
        String token = tokenManager.generateToken(userDetails);
        return token;
    }

}
