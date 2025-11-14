package ninja.oxente.cara_suja.application.mappers;

import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    private final IPasswordHasher passwordHasher;

    public UserDtoMapper(@Autowired IPasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    public UserModel fromRegisterNewUserRequest(RegisterUserRequestDto request) {
        return new UserModel(null, request.name(), request.email(),
            passwordHasher.encode(request.password()), null);
    }

    public UserModel fromUpdateUserRequest(UpdateUserRequestDto request) {
        return new UserModel(null, request.name(), request.email(), null, null);
    }

    public UserListDto toUserList(UserModel model) {
        return new UserListDto(
            model.id(),
            model.name(),
            model.email(),
            null
        );
    }

}
