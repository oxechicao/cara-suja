package ninja.oxente.cara_suja.infrastructure.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public record UserEntity(
    @Id
    String id,

    String name,
    String email,
    String password
) {

}
