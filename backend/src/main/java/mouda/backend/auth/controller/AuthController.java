package mouda.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.dto.LoginRequest;
import mouda.backend.auth.dto.LoginResponse;
import mouda.backend.auth.service.AuthService;
import mouda.backend.common.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginResponse>> login(
        @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok().body(new RestResponse<>(response));
    }
}
