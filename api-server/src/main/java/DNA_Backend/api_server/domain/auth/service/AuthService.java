package DNA_Backend.api_server.domain.auth.service;

import static DNA_Backend.api_server.domain.auth.exception.AuthExceptionMessage.PASSWORD_NOT_MATCH;

import DNA_Backend.api_server.common.security.cookie.CookieManager;
import DNA_Backend.api_server.domain.auth.dto.request.LoginRequest;
import DNA_Backend.api_server.domain.auth.dto.request.UpdateUsernameRequest;
import DNA_Backend.api_server.domain.auth.dto.response.CheckFirstSocialLoginResponse;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.common.exception.DnaApplicationException;
import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.common.security.auth.UserDetailsServiceCustom;
import DNA_Backend.api_server.common.security.jwt.TokenManager;
import DNA_Backend.api_server.domain.user.service.UserCacheService;
import DNA_Backend.api_server.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final UserService userService;
    private final UserCacheService userCacheService;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;
    private final CookieManager cookieManager;

    // PUBLIC - 로그인
    @Transactional(readOnly = true)
    public void login(HttpServletResponse response, LoginRequest requestParam) {
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(requestParam.username());
        User user = userDetails.getUser();
        validatePassword(requestParam.password(), user.getPassword());
        String token = tokenManager.generateToken(userDetails);
        cookieManager.setCookie(response, token);
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
    public void inputNewUsername(HttpServletRequest request, HttpServletResponse response, String username, UpdateUsernameRequest requestParam) {
        cookieManager.deleteCookie(request, response);
        userService.updateUsername(username, requestParam.newUsername());
        UserDetailsCustom userDetails = userDetailsServiceCustom.loadUserByUsername(requestParam.newUsername());
        String token = tokenManager.generateToken(userDetails);
        cookieManager.setCookie(response, token);
    }

    // USER - 로그아웃
    public void logout(HttpServletRequest request, HttpServletResponse response, String username) {
        cookieManager.deleteCookie(request, response);
        userCacheService.deleteUserCache(username);
    }

}
