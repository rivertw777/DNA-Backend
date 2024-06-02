package TourData.backend.domain.user.service;

import TourData.backend.domain.user.dto.UserSignUpRequest;

public interface UserSerivce {
    void signUp(UserSignUpRequest requestParam);
}
