package mouda.backend.auth.service;

import mouda.backend.auth.dto.request.LoginRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @DisplayName("로그인을 시도한다.")
    @Test
    void login() {
        LoginRequest request = new LoginRequest("테바");

        LoginResponse response = authService.login(request);

        System.out.println(response.accessToken());
    }
}
