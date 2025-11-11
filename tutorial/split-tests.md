# Split tests

<!-- TOC -->
* [Split tests](#split-tests)
  * [Introdução](#introdução)
  * [PROS & CONS](#pros--cons)
  * [](#)
<!-- TOC -->

## Introdução

Esse tutorial explica como dividir os testes em diferentes módulos para serem execultados
separadamente. Assim, teremos os módulo `test` que representam os testes unitários e utilizam
banco em memória. O módulo `integrationsTest` que utilizará testcontainers para simular um banco
real e neste tutorial irá focar nos testes de integração com o banco (ex. mongodb). O módulo
`featuresTest` irá utilizar testcaontainers também, mas irá focar no happy path dos fluxos da
aplicação, ou em testes críticos.

## PROS & CONS

PROS:

- Os testes são isolados em todos os níveis (unitários, integração e features).
- As dependências são isoladas, ou seja, não precisamos carregar dependências de testes
  unitários (ex. mockk) para os testes de integração e features.
- Podemos configurar diferentes ambientes para cada tipo de teste.
- Podemos executar os testes separadamente, o que pode ser útil em pipelines de CI/CD.

CONS:

- A configuração inicial é mais complexa, pois precisamos configurar cada módulo de teste
  separadamente.
- Pode haver duplicação de código entre os módulos de teste, especialmente se houver
  configurações comuns.
- A manutenção pode ser mais trabalhosa, pois precisamos garantir que todas as
  dependências e configurações estejam atualizadas em todos os módulos de teste.

## Como fazer

### Criar módulo para os testes de integração

Crie a estrutura de pastas para o novo módulo de testes de integração:

```
└── src
    ├── integrationTest
    │   ├── java
    │   │   └── ninja/oxente/cara_suja/presentation/controllers
    │   │       └── UsersControllerIntegrationTest.kt
    │   └── resources
```

### Configurar o build.gradle

1. Crie um novo sourceSets para os testes de integração

```gradle
sourceSets {
    integrationTest {
        compileClasspath += sourceSets.main.output
        runtimeClasspath += sourceSets.main.output
    }
}
```

2. Altere as configurações de teste para incluir o novo sourceSet

Neste passo você irá adicionar `extendsFrom` para as configurações do novo sourceSet

```gradle 
configurations {
    ...
    integrationTestImplementation.extendsFrom implementation
    integrationTestImplementation.extendsFrom testImplementation
    integrationTestRuntimeOnly.extendsFrom runtimeOnly
}
```

3. Crie uma nova task para executar os testes de integração

```gradle
tasks.register('integrationTest', Test) {
    description = 'Runs integration tests.'
    group = 'verification'

    testClassesDirs = sourceSets.integrationTest.output.classesDirs
    classpath = sourceSets.integrationTest.runtimeClasspath
    shouldRunAfter test

    useJUnitPlatform()

    testLogging {
        events "passed"
    }
}
```

4. Adicione a dependência da task de testes de integração na task `check`

```gradle
check.dependsOn integrationTest
```

5. Adicione as dependências necessárias para os testes de integração

Abaixo é adicionado o testcontainers para mongodb como exemplo, mas você pode adicionar
outras dependências conforme a necessidade dos seus testes.

A dependência será instalada somente para o módulo de testes de integração.

```gradle
dependencies {
  ...
  implementation platform('org.testcontainers:testcontainers-bom:2.0.1')
  integrationTestImplementation('org.testcontainers:testcontainers-mongodb')
}
```

## Referencias

[gradle testing](https://docs.gradle.org/8.5/userguide/java_testing.html#sec:configuring_java_integration_tests)

[organizing tests](https://docs.gradle.org/8.5/userguide/organizing_tests.html)