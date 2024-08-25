package DNA_Backend.api_server.global.security.dto;

public record LoginRequest(String username, String email, String password) {
}
