package mouda.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.dto.request.LoginRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.auth.service.AuthService;
import mouda.backend.common.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok().body(new RestResponse<>(response));
    }
}
