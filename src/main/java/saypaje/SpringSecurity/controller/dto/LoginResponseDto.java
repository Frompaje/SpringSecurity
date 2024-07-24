package saypaje.SpringSecurity.controller.dto;

public record LoginResponseDto(String acessToken, long expiresIn) {
}
