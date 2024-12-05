package DNA_Backend.api_server.domain.auth.service;

import static DNA_Backend.api_server.domain.auth.exception.AuthExceptionMessage.PASSWORD_NOT_MATCH;

import DNA_Backend.api_server.domain.auth.dto.request.LoginRequest;
import DNA_Backend.api_server.domain.auth.dto.response.CheckFirstSocialLoginResponse;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.common.security.auth.UserDetailsServiceCustom;
import DNA_Backend.api_server.common.security.jwt.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    // PUBLIC - 로그인
    @Transactional(readOnly = true)
    public String login(LoginRequest requestParam) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(requestParam.username());
        User user = userDetails.getUser();
        validatePassword(requestParam.password(), user.getPassword());
        return tokenManager.generateToken(userDetails);
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

    // 토큰 얻기
    @Transactional(readOnly = true)
    public String getToken(String newUsername) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(newUsername);
        return tokenManager.generateToken(userDetails);
    }

}
