package ninja.oxente.cara_suja.domains.account;

import java.util.UUID;

public record AccountModel(UUID id, String name, UUID karteiraId, TemplateModel template) {

}
