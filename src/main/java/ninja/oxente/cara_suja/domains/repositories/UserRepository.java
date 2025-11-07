package ninja.oxente.cara_suja.domains.repositories;

import java.util.List;
import ninja.oxente.cara_suja.domains.user.UserModel;

public interface UserRepository {

    UserModel findById(String id);

    List<UserModel> findAll();

    UserModel save(UserModel userModel);

    UserModel update(UserModel userModel, String id);

    UserModel findByEmail(String email);
}
