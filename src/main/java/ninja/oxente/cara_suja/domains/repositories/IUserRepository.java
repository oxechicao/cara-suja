package ninja.oxente.cara_suja.domains.repositories;

import ninja.oxente.cara_suja.domains.user.UserModel;

public interface IUserRepository extends BaseRepository<UserModel> {

    UserModel findByEmail(String email);
}
