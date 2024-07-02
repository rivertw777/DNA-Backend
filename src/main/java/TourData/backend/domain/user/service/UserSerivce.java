package TourData.backend.domain.user.service;

import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.model.User;

public interface UserSerivce {
    void signUp(UserSignUpRequest requestParam);
    User findUser(String username);
}
