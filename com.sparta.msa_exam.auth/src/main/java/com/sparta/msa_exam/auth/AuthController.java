package com.sparta.msa_exam.auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.port}")
    private String serverPort;
    private final AuthService authService;

    @GetMapping("/auth/signIn")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestParam String user_id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(new AuthResponse(authService.createAccessToken(user_id)));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String access_token;
    }
}