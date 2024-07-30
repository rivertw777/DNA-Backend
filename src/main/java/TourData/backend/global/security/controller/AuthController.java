package TourData.backend.global.security.controller;

import TourData.backend.domain.user.model.User;
import TourData.backend.global.security.dto.AuthDto.NewUsernameRequest;
import TourData.backend.global.security.dto.AuthDto.CheckFirstLoginResponse;
import TourData.backend.global.security.service.AuthService;
import TourData.backend.global.security.utils.ResponseWriter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ResponseWriter responseWriter;

    @Operation(summary = "소셜 계정 최초 로그인 확인")
    @GetMapping("/login/check")
    public ResponseEntity<CheckFirstLoginResponse> checkFirstLogin(@AuthenticationPrincipal(expression = "username") String username) {
        CheckFirstLoginResponse response = authService.checkFirstLogin(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 새 이름 입력")
    @PostMapping("/names")
    public ResponseEntity<Void> setUsername(HttpServletRequest request, HttpServletResponse response,
                                            @AuthenticationPrincipal(expression = "user") User user,
                                            @Valid @RequestBody NewUsernameRequest requestParam) {
        responseWriter.deleteCookie(request, response);
        authService.setUsername(user, requestParam);
        String token = authService.getToken(requestParam);
        responseWriter.setCookie(response, token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        responseWriter.deleteCookie(request, response);
        return ResponseEntity.ok().build();
    }

}
