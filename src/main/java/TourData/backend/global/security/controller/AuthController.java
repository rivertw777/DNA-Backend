package TourData.backend.global.security.controller;

import TourData.backend.global.security.dto.CheckAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @Operation(summary = "인증 확인")
    @GetMapping("/check")
    public ResponseEntity<CheckAuthenticationResponse> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CheckAuthenticationResponse response = new CheckAuthenticationResponse(authentication.isAuthenticated());
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
