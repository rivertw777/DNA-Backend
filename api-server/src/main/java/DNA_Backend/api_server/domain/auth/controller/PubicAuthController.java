package DNA_Backend.api_server.domain.auth.controller;

import DNA_Backend.api_server.domain.auth.dto.request.LoginRequest;
import DNA_Backend.api_server.domain.auth.service.AuthService;
import DNA_Backend.api_server.global.security.cookie.CookieManager;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/auth")
public class PubicAuthController {

    private final AuthService authService;
    private final CookieManager cookieManager;

    @Operation(summary = "PUBLIC - 로그인")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest requestParam, HttpServletResponse response) {
        String token = authService.login(requestParam);
        cookieManager.setCookie(response, token);
        return ResponseEntity.ok().build();
    }

}
