package br.com.more_light.resources;

import br.com.more_light.security.dto.AuthRequest;
import br.com.more_light.security.dto.AuthResponse;
import br.com.more_light.security.dto.RegisterRequest;
import br.com.more_light.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // URL base para todos os endpoints deste controller
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService Authservice;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(Authservice.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(Authservice.authenticate(request));
    }
}