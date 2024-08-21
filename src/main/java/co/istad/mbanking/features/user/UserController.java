package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }


}
