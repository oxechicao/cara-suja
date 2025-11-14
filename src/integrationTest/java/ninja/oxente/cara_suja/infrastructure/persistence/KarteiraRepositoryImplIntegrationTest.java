package ninja.oxente.cara_suja.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import ninja.oxente.cara_suja.BaseIntegrationTest;
import ninja.oxente.cara_suja.builders.KarteiraModelBuilder;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.KarteiraPersistenceMapper;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.KarteiraMongoRepository;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.KarteiraRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DisplayName("Karteira Repository Integration Tests")
public class KarteiraRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final KarteiraMongoRepository mongoRepository;
    private final KarteiraRepository karteiraRepository;

    public KarteiraRepositoryImplIntegrationTest(
        @Autowired KarteiraMongoRepository mongoRepository
    ) {
        this.mongoRepository = mongoRepository;
        this.karteiraRepository = new KarteiraRepository(
            mongoRepository,
            new KarteiraPersistenceMapper()
        );
    }

    @Nested
    @DisplayName("GIVEN i want to be sure that karteira collection exists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class KarteiraCollectionTests {

        private final KarteiraModel modelMock = new KarteiraModelBuilder().build();
        private KarteiraModel modelSaved;

        @BeforeAll
        @AfterAll
        void setUp(@Autowired KarteiraMongoRepository mongoRepository) {
            mongoRepository.deleteAll();
        }

        @Test
        @Order(1)
        @DisplayName("SHOULD karteira collection be empty BEFORE tests")
        public void shouldKarteiraCollectionBeEmptyBeforeTests() {
            long count = mongoRepository.count();
            assertEquals(0, count);
            List<KarteiraModel> list = karteiraRepository.findAll();
            assertEquals(0, list.size());
        }

        @Test
        @Order(2)
        @DisplayName("SHOULD insert new Karteira document WHEN save is called")
        public void shouldInsertNewKarteiraDocumentWhenSaveIsCalled() {
            assertNull(modelSaved);
            System.out.println(modelMock);
            modelSaved = karteiraRepository.save(modelMock);
            System.out.println("Saved Model ID: " + modelSaved);

            assertNotNull(modelSaved);
        }

        @Test
        @Order(3)
        @DisplayName("SHOULD find Karteira document by id WHEN findById is called")
        public void shouldFindKarteiraDocumentByIdWhenFindByIdIsCalled() {
            KarteiraModel foundModel = karteiraRepository.findById(modelSaved.id());
            assertNotNull(foundModel);
            assertEquals(foundModel, modelSaved);
        }

        @Test
        @Order(4)
        @DisplayName("SHOULD update Karteira document WHEN update is called")
        public void shouldUpdateKarteiraDocumentWhenUpdateIsCalled() {
            KarteiraModel updatedModel = new KarteiraModelBuilder(modelSaved)
                .name("Updated Carteira Name")
                .build();

            KarteiraModel resultModel = karteiraRepository.update(updatedModel, modelSaved.id());
            assertNotNull(resultModel);
            assertEquals(updatedModel, resultModel);
        }

        @Test
        @Order(5)
        @DisplayName("SHOULD return all Karteira documents WHEN findAll is called")
        public void shouldReturnAllKarteiraDocumentsWhenFindAllIsCalled() {
            List<KarteiraModel> allModels = karteiraRepository.findAll();
            assertNotNull(allModels);
            assertEquals(1, allModels.size());
            assertEquals(modelSaved.id(), allModels.getFirst().id());
        }
    }
}
