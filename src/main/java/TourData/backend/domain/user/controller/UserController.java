package TourData.backend.domain.user.controller;

import TourData.backend.domain.user.dto.EmailDto.SendEmailCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyEmailCodeRequest;
import TourData.backend.domain.user.dto.EmailDto.VerifyEmailCodeResponse;
import TourData.backend.domain.user.dto.UserDto.CheckDuplicateUsernameRequest;
import TourData.backend.domain.user.dto.UserDto.CheckDuplicateUsernameResponse;
import TourData.backend.domain.user.dto.UserDto.SignUpRequest;
import TourData.backend.domain.user.dto.UserDto.UsernameResponse;
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
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest reqeustParam) {
        userService.signUp(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이름 중복 여부 확인")
    @PostMapping("/name/check")
    public ResponseEntity<CheckDuplicateUsernameResponse> CheckDuplicateUsername(@Valid @RequestBody CheckDuplicateUsernameRequest requestParam) {
        CheckDuplicateUsernameResponse response = userService.CheckDuplicateUsername(requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 이름 조회")
    @GetMapping("/name")
    public ResponseEntity<UsernameResponse> getUsername(@AuthenticationPrincipal(expression = "username") String username) {
        UsernameResponse response = userService.getUsername(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이메일 인증 코드 전송")
    @PostMapping("/email/code/send")
    public ResponseEntity<Void> sendEmailCode(@Valid @RequestBody SendEmailCodeRequest reqeustParam) {
        userService.sendEmailCode(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/email/code/verify")
    public ResponseEntity<VerifyEmailCodeResponse> verifyEmailCode(@Valid @RequestBody VerifyEmailCodeRequest reqeustParam) {
        VerifyEmailCodeResponse response = userService.verifyEmailCode(reqeustParam);
        return ResponseEntity.ok(response);
    }

}
