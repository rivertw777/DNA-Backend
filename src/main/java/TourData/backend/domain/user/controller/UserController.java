package TourData.backend.domain.user.controller;

import TourData.backend.domain.user.dto.UserDto;
import TourData.backend.domain.user.dto.UserDto.UserNameResponse;
import TourData.backend.domain.user.service.UserSerivce;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserSerivce userSerivce;

    @Operation(summary = "회원가입")
    @PostMapping
    public void signUp(@Valid @RequestBody UserDto.UserSignUpRequest reqeustParam) {
        userSerivce.signUp(reqeustParam);
    }

    @Operation(summary = "인증된 사용자 이름")
    @GetMapping("/name")
    public UserNameResponse getUserName(@AuthenticationPrincipal(expression = "username") String username) {
        return userSerivce.getUserName(username);
    }

}
