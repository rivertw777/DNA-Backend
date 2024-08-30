package DNA_Backend.api_server.global.security.oauth;

import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.user.repository.UserRepository;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import DNA_Backend.api_server.global.security.oauth.provider.GoogleUserInfo;
import DNA_Backend.api_server.global.security.oauth.provider.KakaoUserInfo;
import DNA_Backend.api_server.global.security.oauth.provider.OAuth2UserInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2UserServiceCustom extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // 사용자 정보 객체 생성
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        // 유저 조회
        Optional<User> userOptional =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setEmail(oAuth2UserInfo.getEmail());
        } else {
            // 회원 가입
            String username = "default_" + oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
            user = User.createUser(username, oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
            userRepository.save(user);
        }
        return new UserDetailsCustom(user, oAuth2User.getAttributes());
    }

}