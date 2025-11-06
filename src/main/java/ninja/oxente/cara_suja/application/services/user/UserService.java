package ninja.oxente.cara_suja.application.services.user;

import ninja.oxente.cara_suja.application.mappers.UserDtoMapper;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.UserPersistenceMapper;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserRepository;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper,
        UserPersistenceMapper userPersistenceMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    public String createNewUser(RegisterUserRequest userRequest) {
        UserEntity entity = this.userPersistenceMapper.mapModelToEntity(
            this.userDtoMapper.fromRegisterNewUserRequest(userRequest)
        );

        UserEntity saved = this.userRepository.save(entity);
        return saved.id();
    }
}
