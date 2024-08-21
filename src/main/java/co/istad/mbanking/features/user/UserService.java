package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.RegisterRequest;

public interface UserService {

    void register(RegisterRequest registerRequest);

}
