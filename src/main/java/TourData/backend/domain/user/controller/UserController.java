package TourData.backend.domain.user.controller;

import TourData.backend.domain.user.dto.EmailDto.EmailVerificationResponse;
import TourData.backend.domain.user.dto.EmailDto.VerifyCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.SendCodeRequest;
import TourData.backend.domain.user.dto.UserDto.UserSignUpRequest;
import TourData.backend.domain.user.dto.UserDto.UsernameResponse;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameRequest;
import TourData.backend.domain.user.dto.UserDto.ValidateDuplicateUsernameResponse;
import TourData.backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;

    @Operation(summary = "회원 가입")
    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignUpRequest reqeustParam) {
        userService.signUp(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 이름 중복 체크")
    @PostMapping("/names/validate")
    public ResponseEntity<ValidateDuplicateUsernameResponse> validateDuplicateUserName(@Valid @RequestBody ValidateDuplicateUsernameRequest requestParam) {
        ValidateDuplicateUsernameResponse response = userService.validateDuplicateUserName(requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 이름 조회")
    @GetMapping("/names")
    public ResponseEntity<UsernameResponse> getUserName(@AuthenticationPrincipal(expression = "username") String username) {
        UsernameResponse response = userService.getUserName(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이메일 인증 코드 전송")
    @PostMapping("/emails/code")
    public ResponseEntity<Void> sendCode(@Valid @RequestBody SendCodeRequest reqeustParam) {
        userService.sendCode(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/emails/verify")
    public ResponseEntity<EmailVerificationResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest reqeustParam) {
        EmailVerificationResponse response = userService.verifyCode(reqeustParam);
        return ResponseEntity.ok(response);
    }

}
