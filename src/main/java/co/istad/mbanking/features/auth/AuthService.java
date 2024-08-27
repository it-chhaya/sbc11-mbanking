package co.istad.mbanking.features.auth;

import co.istad.mbanking.features.auth.dto.RegisterRequest;
import co.istad.mbanking.features.auth.dto.VerifyRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    void verify(VerifyRequest verifyRequest);

    void register(RegisterRequest registerRequest) throws MessagingException;
}
