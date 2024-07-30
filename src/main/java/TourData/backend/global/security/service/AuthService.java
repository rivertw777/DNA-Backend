package TourData.backend.global.security.service;

import TourData.backend.domain.user.model.User;
import TourData.backend.global.security.auth.CustomUserDetails;
import TourData.backend.global.security.auth.CustomUserDetailsService;
import TourData.backend.global.security.dto.AuthDto.CheckFirstLoginResponse;
import TourData.backend.global.security.dto.AuthDto.NewUsernameRequest;
import TourData.backend.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;

    // 소셜 계정 최초 로그인 확인
    public CheckFirstLoginResponse checkFirstLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");
        return new CheckFirstLoginResponse(isFirstLogin);
    }

    // 사용자 새 이름 입력
    @Transactional
    public void setUsername(User user, NewUsernameRequest requestParam) {
        user.setUserName(requestParam.newUsername());
    }

    // 토큰 얻기
    @Transactional(readOnly = true)
    public String getToken(NewUsernameRequest requestParam) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(requestParam.newUsername());
        String token = tokenProvider.generateToken(userDetails);
        return token;
    }

}
