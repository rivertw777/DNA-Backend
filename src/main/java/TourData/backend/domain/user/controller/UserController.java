package TourData.backend.domain.user.controller;

import TourData.backend.domain.user.dto.UserSignUpRequest;
import TourData.backend.domain.user.service.UserSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserSerivce userSerivce;

    // 회원 가입
    @PostMapping
    public void signUp(@Valid @RequestBody UserSignUpRequest reqeustParam){
        userSerivce.signUp(reqeustParam);
    }

}
