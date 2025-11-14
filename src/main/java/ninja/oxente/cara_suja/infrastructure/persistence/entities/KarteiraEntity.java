package ninja.oxente.cara_suja.infrastructure.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("karteiras")
public record KarteiraEntity(
    @Id
    String id,

    String name,
    Integer limit,
    Integer goal
) {

}
