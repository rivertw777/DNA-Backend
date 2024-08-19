package DNA_Backend.api_server.global.security.service;

import DNA_Backend.api_server.domain.user.model.User;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.global.security.auth.UserDetailsServiceCustom;
import DNA_Backend.api_server.global.security.dto.SecurityDto.CheckFirstSocialLoginResponse;
import DNA_Backend.api_server.global.security.jwt.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final UserService userService;
    private final TokenManager tokenManager;

    // 사용자 소셜 계정 최초 로그인 확인
    public CheckFirstSocialLoginResponse checkFirstSocialLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");

        return new CheckFirstSocialLoginResponse(isFirstLogin);
    }

    // 사용자 새 이름 입력
    @Transactional
    public void updateUsername(String username, String newUsername) {
        User user = userService.findUser(username);
        user.setUserName(newUsername);
    }

    // 토큰 얻기
    @Transactional(readOnly = true)
    public String getToken(String newUsername) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(newUsername);
        String token = tokenManager.generateToken(userDetails);
        return token;
    }

}
