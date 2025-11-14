package ninja.oxente.cara_suja.builders;

import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;

public class KarteiraModelBuilder {

    private Integer limit = 10000;
    private Integer goal = 5000;
    private String id = "karteira-id-123";
    private String name = "Carteira Teste";

    public KarteiraModelBuilder() {
    }

    public KarteiraModelBuilder(KarteiraModel model) {
        this.id = model.id();
        this.name = model.name();
        this.limit = model.limit();
        this.goal = model.goal();
    }

    public KarteiraModelBuilder id(String id) {
        this.id = id;
        return this;
    }

    public KarteiraModelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public KarteiraModelBuilder limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public KarteiraModelBuilder goal(Integer goal) {
        this.goal = goal;
        return this;
    }

    public KarteiraModel build() {
        return new KarteiraModel(
            this.id,
            this.name,
            this.limit,
            this.goal,
            null
        );
    }
}
