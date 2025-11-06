package ninja.oxente.cara_suja.application.mappers;

import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserModel fromRegisterNewUserRequest(RegisterUserRequest request) {
        return new UserModel(null, request.name(), request.email(),
            request.password(), null);
    }
}
