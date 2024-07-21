package TourData.backend.domain.user.service;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UserNameResponse;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUserNameRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUserNameResponse;
import TourData.backend.domain.user.model.User;

public interface UserSerivce {
    void signUp(UserSignUpRequest requestParam);
    ValidateDuplicateUserNameResponse validateDuplicateUserName(ValidateDuplicateUserNameRequest requestParam);
    User findUser(String username);
    UserNameResponse getUserName(String username);
    void sendCode(SendCodeRequest requestParam);
    EmailVerificationResponse verifyCode(VerifyCodeRequest requestParam);
}
