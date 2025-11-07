package ninja.oxente.cara_suja.application.mappers;

import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserModel fromRegisterNewUserRequest(RegisterUserRequest request) {
        return new UserModel(null, request.name(), request.email(),
            request.password(), null);
    }

    public UserModel fromUpdateUserRequest(UpdateUserRequest request) {
        return new UserModel(null, request.name(), request.email(), null, null);
    }

    public UserList toUserList(UserModel model) {
        return new UserList(
            model.id(),
            model.name(),
            model.email(),
            null
        );
    }

}
