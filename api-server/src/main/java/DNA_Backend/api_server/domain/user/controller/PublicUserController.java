package DNA_Backend.api_server.domain.user.controller;

import DNA_Backend.api_server.domain.user.dto.request.CheckDuplicateUsernameRequest;
import DNA_Backend.api_server.domain.user.dto.request.SendEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.request.SignUpRequest;
import DNA_Backend.api_server.domain.user.dto.request.VerifyEmailCodeRequest;
import DNA_Backend.api_server.domain.user.dto.response.CheckDuplicateUsernameResponse;
import DNA_Backend.api_server.domain.user.dto.response.VerifyEmailCodeResponse;
import DNA_Backend.api_server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users")
public class PublicUserController {

    private final UserService userService;

    @Operation(summary = "PUBLIC - 회원 가입")
    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest reqeustParam) {
        userService.signUp(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PUBLIC - 이름 중복 여부 확인")
    @PostMapping("/name/check")
    public ResponseEntity<CheckDuplicateUsernameResponse> CheckDuplicateUsername(@Valid @RequestBody CheckDuplicateUsernameRequest requestParam) {
        CheckDuplicateUsernameResponse response = userService.CheckDuplicateUsername(requestParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "PUBLIC - 이메일 인증 코드 전송")
    @PostMapping("/email/code/send")
    public ResponseEntity<Void> sendEmailCode(@Valid @RequestBody SendEmailCodeRequest reqeustParam) {
        userService.sendEmailCode(reqeustParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PUBLIC - 이메일 인증 코드 검증")
    @PostMapping("/email/code/verify")
    public ResponseEntity<VerifyEmailCodeResponse> verifyEmailCode(@Valid @RequestBody VerifyEmailCodeRequest reqeustParam) {
        VerifyEmailCodeResponse response = userService.verifyEmailCode(reqeustParam);
        return ResponseEntity.ok(response);
    }

}
