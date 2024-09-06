package DNA_Backend.api_server.domain.user.controller;

import DNA_Backend.api_server.domain.user.dto.EmailDto.SendEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.EmailDto.UserPopupStatusResponse;
import DNA_Backend.api_server.domain.user.dto.EmailDto.VerifyEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.EmailDto.VerifyEmailCodeResponse;
import DNA_Backend.api_server.domain.user.dto.UserDto.CheckDuplicateUsernameRequest;
import DNA_Backend.api_server.domain.user.dto.UserDto.SignUpRequest;
import DNA_Backend.api_server.domain.user.dto.UserDto.CheckDuplicateUsernameResponse;
import DNA_Backend.api_server.domain.user.dto.UserDto.UsernameResponse;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.global.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "PUBLIC - 회원 가입")
    @PostMapping("/api/public/users")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest reqeustParam) {
        userService.signUp(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PUBLIC - 이름 중복 여부 확인")
    @PostMapping("/api/public/users/name/check")
    public ResponseEntity<CheckDuplicateUsernameResponse> CheckDuplicateUsername(@Valid @RequestBody CheckDuplicateUsernameRequest requestParam) {
        CheckDuplicateUsernameResponse response = userService.CheckDuplicateUsername(requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 이름 조회")
    @GetMapping("/api/users/name")
    public ResponseEntity<UsernameResponse> getUsername(@AuthenticationPrincipal(expression = "username") String username) {
        UsernameResponse response = userService.getUsername(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 이메일 인증 코드 전송")
    @PostMapping("/api/public/users/email/code/send")
    public ResponseEntity<Void> sendEmailCode(@Valid @RequestBody SendEmailCodeRequest reqeustParam) {
        userService.sendEmailCode(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PUBLIC - 이메일 인증 코드 검증")
    @PostMapping("/api/public/users/email/code/verify")
    public ResponseEntity<VerifyEmailCodeResponse> verifyEmailCode(@Valid @RequestBody VerifyEmailCodeRequest reqeustParam) {
        VerifyEmailCodeResponse response = userService.verifyEmailCode(reqeustParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 팝업 상태 조회")
    @GetMapping("/api/users/popup-status")
    public ResponseEntity<UserPopupStatusResponse> getUserPopupStatus(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        UserPopupStatusResponse response = userService.getUserPopupStatus(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 팝업 상태 초기화")
    @PatchMapping("/api/users/popup-status")
    public ResponseEntity<Void> UpdateUserPopupStatus(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        userService.UpdateUserPopupStatus(userId);
        return ResponseEntity.ok().build();
    }

}
