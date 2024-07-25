package TourData.backend.global.security.service;

import TourData.backend.domain.user.model.User;
import TourData.backend.domain.user.service.UserService;
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

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public CheckFirstLoginResponse checkFirstLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");
        return new CheckFirstLoginResponse(isFirstLogin);
    }

    // 사용자 새 이름 입력
    @Transactional
    public void setUserName(String username, NewUsernameRequest requestParam) {
        User user = userService.findUser(username);
        user.setUserName(requestParam.newUsername());
    }

    @Transactional(readOnly = true)
    public String getToken(NewUsernameRequest requestParam) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(requestParam.newUsername());
        String token = tokenProvider.generateToken(userDetails);
        return token;
    }

}
