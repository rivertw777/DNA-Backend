package TourData.backend.domain.user.service;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UserNameResponse;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.model.User;

public interface UserSerivce {
    void signUp(UserSignUpRequest requestParam);
    User findUser(String username);
    UserNameResponse getUserName(String username);
    void sendCode(SendCodeRequest reqeustParam);
    EmailVerificationResponse verifyCode(VerifyCodeRequest reqeustParam);
}
