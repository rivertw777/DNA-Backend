package TourData.backend.global.security.service;

import TourData.backend.global.security.dto.CheckFirstLoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public CheckFirstLoginResponse checkFirstLogin(String username) {
        boolean isFirstLogin = username.startsWith("default");
        return new CheckFirstLoginResponse(isFirstLogin);
    }

}
