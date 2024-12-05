package DNA_Backend.api_server.domain.user.controller;

import DNA_Backend.api_server.domain.user.dto.response.UserPopupStatusResponse;
import DNA_Backend.api_server.domain.user.dto.response.UsernameResponse;
import DNA_Backend.api_server.domain.user.service.UserService;
import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "USER - 이름 조회")
    @GetMapping("/name")
    public ResponseEntity<UsernameResponse> getUsername(@AuthenticationPrincipal(expression = "username") String username) {
        UsernameResponse response = userService.getUsername(username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 팝업 상태 조회")
    @GetMapping("/popup-status")
    public ResponseEntity<UserPopupStatusResponse> getUserPopupStatus(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        UserPopupStatusResponse response = userService.getUserPopupStatus(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 팝업 상태 초기화")
    @PatchMapping("/popup-status")
    public ResponseEntity<Void> UpdateUserPopupStatus(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        userService.UpdateUserPopupStatus(userId);
        return ResponseEntity.ok().build();
    }

}
