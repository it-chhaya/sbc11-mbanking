package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.auth.dto.RegisterRequest;
import co.istad.mbanking.features.user.dto.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromCreateUserRequest(CreateUserRequest createUserRequest);

    User fromRegisterRequest(RegisterRequest registerRequest);

}
