package TourData.backend.domain.user.controller;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.dto.UserDto.UserNameResponse;
import TourData.backend.domain.user.service.UserSerivce;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserSerivce userService;

    @Operation(summary = "회원가입")
    @PostMapping
    public void signUp(@Valid @RequestBody UserSignUpRequest reqeustParam) {
        userService.signUp(reqeustParam);
    }

    @Operation(summary = "인증된 사용자 이름")
    @GetMapping("/name")
    public UserNameResponse getUserName(@AuthenticationPrincipal(expression = "username") String username) {
        return userService.getUserName(username);
    }

    @Operation(summary = "이메일 인증 코드 전송")
    @PostMapping("/emails/verify")
    public ResponseEntity<Void> sendCode(@Valid @RequestBody SendCodeRequest reqeustParam) {
        userService.sendCode(reqeustParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @GetMapping("/emails/verify")
    public ResponseEntity<EmailVerificationResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest reqeustParam) {
        EmailVerificationResponse response = userService.verifyCode(reqeustParam);
        return ResponseEntity.ok(response);
    }

}
