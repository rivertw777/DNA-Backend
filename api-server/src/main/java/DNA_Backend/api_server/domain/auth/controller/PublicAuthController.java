package DNA_Backend.api_server.domain.auth.controller;

import DNA_Backend.api_server.domain.auth.dto.request.LoginRequest;
import DNA_Backend.api_server.domain.auth.service.AuthService;
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
public class PublicAuthController {

    private final AuthService authService;

    @Operation(summary = "PUBLIC - 로그인")
    @PostMapping("/login")
    public ResponseEntity<Void> login(HttpServletResponse response, @Valid @RequestBody LoginRequest requestParam) {
        authService.login(response, requestParam);
        return ResponseEntity.ok().build();
    }

}
