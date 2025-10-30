package ninja.oxente.cara_suja.models;

import java.util.UUID;

public record AccountModel(UUID id, String name, UUID karteiraId, TemplateModel template) {

}
