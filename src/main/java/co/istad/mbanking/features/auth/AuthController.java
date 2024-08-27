package co.istad.mbanking.features.auth;

import co.istad.mbanking.features.auth.dto.RegisterRequest;
import co.istad.mbanking.features.auth.dto.VerifyRequest;
import co.istad.mbanking.features.user.dto.CreateUserRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/verify")
    void verify(@Valid @RequestBody VerifyRequest verifyRequest) {
        authService.verify(verifyRequest);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void register(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException {
        authService.register(registerRequest);
    }

}
