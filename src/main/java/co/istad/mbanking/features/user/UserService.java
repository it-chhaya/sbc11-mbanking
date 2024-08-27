package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.CreateUserRequest;

public interface UserService {

    void register(CreateUserRequest createUserRequest);

}
