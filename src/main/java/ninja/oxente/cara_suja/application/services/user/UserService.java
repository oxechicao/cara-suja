package ninja.oxente.cara_suja.application.services.user;

import java.util.List;
import java.util.stream.Collectors;
import ninja.oxente.cara_suja.application.mappers.UserDtoMapper;
import ninja.oxente.cara_suja.domains.exceptions.EntityNotFoundException;
import ninja.oxente.cara_suja.domains.repositories.UserRepository;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDtoMapper userDtoMapper;

    private final UserRepository userRepository;

    public UserService(
        UserDtoMapper userDtoMapper,
        @Autowired UserRepository userRepository
    ) {
        this.userDtoMapper = userDtoMapper;
        this.userRepository = userRepository;
    }

    public String createNewUser(RegisterUserRequestDto userRequest) {
        UserModel userSaved = userRepository.save(
            this.userDtoMapper.fromRegisterNewUserRequest(userRequest)
        );

        return userSaved.id();
    }

    public List<UserListDto> getAllUsers() {
        List<UserModel> entities = this.userRepository.findAll();
        return entities.stream()
            .map(this.userDtoMapper::toUserList)
            .collect(Collectors.toList());
    }

    public UserListDto getUserById(String id) throws EntityNotFoundException {
        UserModel userModel = userRepository.findById(id);

        if (userModel == null) {
            throw new EntityNotFoundException("User not found");
        }

        return userDtoMapper.toUserList(userModel);
    }

    public UserListDto updateUser(String id, UpdateUserRequestDto request)
        throws EntityNotFoundException {
        UserModel requestModel = userDtoMapper.fromUpdateUserRequest(request);
        UserModel userUpdated = userRepository.update(requestModel, id);

        if (userUpdated == null) {
            throw new EntityNotFoundException("User not found");
        }

        return userDtoMapper.toUserList(userUpdated);
    }
}
