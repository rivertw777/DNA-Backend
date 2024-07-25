package TourData.backend.global.security.controller;

import TourData.backend.global.security.dto.CheckFirstLoginResponse;
import TourData.backend.global.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 계정 최초 로그인 확인")
    @GetMapping("/login/check")
    public ResponseEntity<CheckFirstLoginResponse> checkFirstLogin(@AuthenticationPrincipal(expression = "username") String username) {
        CheckFirstLoginResponse response = authService.checkFirstLogin(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies()).ifPresent(cookies ->
                Arrays.stream(cookies)
                        .forEach(cookie -> {
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }));
        return ResponseEntity.ok().build();
    }

}
