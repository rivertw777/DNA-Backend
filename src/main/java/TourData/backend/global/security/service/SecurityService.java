package TourData.backend.global.security.service;

import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.service.UserService;
import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.dto.SecurityDto.CheckFirstLoginResponse;
import TourData.backend.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    // 소셜 계정 최초 로그인 확인
    public CheckFirstLoginResponse checkFirstLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");
        return new CheckFirstLoginResponse(isFirstLogin);
    }

    // 사용자 새 이름 입력
    @Transactional
    public void setUsername(String username, String newUsername) {
        User user = userService.findUser(username);
        user.setUserName(newUsername);
    }

    // 토큰 얻기
    @Transactional(readOnly = true)
    public String getToken(String newUsername) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(newUsername);
        String token = tokenProvider.generateToken(userDetails);
        return token;
    }

}
