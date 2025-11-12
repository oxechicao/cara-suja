# Github Actions

Neste tutorial, iremos configurar o Github actions para rodar os testes automatizados de um
projeto Spring Boot com Gradle, Java, Sdk21.

Neste workflow:

1. Build da aplicação, e somente o build
2. Rodar os testes automatizados a partir do build em paralelo

Abaixo o resultado final do arquivo de workflow que iremos criar. Para ver a explicação leia até
o final:

```yaml
name: Main Workflow

on:
  push:
    branches:
      - main

jobs:
  build:
    name: build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle

      - name: Build (compile, package) without running tests
        run: ./gradlew clean assemble -x test -x integrationTest --no-daemon

      - name: Upload build artifact
        uses: actions/upload-artifact@v3
        with:
          name: build-artifacts
          path: build/**

  unit-test:
    name: unit-test
    needs: build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle

      - name: Download build artifact from build job
        uses: actions/download-artifact@v3
        with:
          name: build-artifacts
          path: build

      - name: Run unit tests
        run: ./gradlew test --no-daemon

  integration-test:
    name: integration-test
    needs: build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle

      - name: Download build artifact from build job
        uses: actions/download-artifact@v3
        with:
          name: build-artifacts
          path: build

      - name: Run integration tests
        run: ./gradlew integrationTest --no-daemon
```

## Definindo o workflow

Github Actions utiliza arquivos YAML para definir workflows, localizados em `.github/workflows/`.

### 1. Crie o arquivo do workflow

Crie um arquivo chamado `main.yml` em `.github/workflows/`.

```sh
touch .github/workflows/main.yml
```

### 2. Defina quais serão os gatilhos (triggers) para o workflow.

Vamos configurar o workflow para rodar quando fizermos um **push** na `main`.

```yaml
name: Main Workflow

on:
  push:
    branches:
      - main
```

Gatilhos são eventos que ocorrem no repositório que iniciam o workflow. Pode ser quando fizer
push numa branch, abrir um pull request, ou até manualmente. Existem diferentes formas de
iniciar uma pipe.

### 3. Definir o job que fará o build

Iremos definir o job de build, que irá configurar o ambiente e rodar o build do projeto.

```yaml
# Continuando do passo anterior

jobs:
  build:
    name: build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle

      - name: Build (compile, package) without running tests
        run: ./gradlew clean assemble -x test -x integrationTest --no-daemon

      - name: Upload build artifact
        uses: actions/upload-artifact@v3
        with:
          name: build-artifacts
          path: build/** 
```

### 4. Definir os jobs de tests

Os jobs de testes irão depender do job de build, ou seja, só irão rodar se o build for bem sucedido.
Além disso irão utilizar o resultado do build, para assim evitar ter que compilar o código
novamente.

```yaml
unit-test:
  name: unit-test
  needs: build
  runs-on: ubuntu-latest

  steps:
    - name: Checkout repository
      uses: actions/checkout@v3

    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        distribution: 'temurin'
        java-version: '21'
        cache: gradle

    - name: Download build artifact from build job
      uses: actions/download-artifact@v3
      with:
        name: build-artifacts
        path: build

    - name: Run unit tests
      run: ./gradlew test --no-daemon

  integration-test:
    name: integration-test
    needs: build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle

      - name: Download build artifact from build job
        uses: actions/download-artifact@v3
        with:
          name: build-artifacts
          path: build

      - name: Run integration tests
        run: ./gradlew integrationTest --no-daemon
```

## Estrutura básica do workflow

- **Workflows**: Um arquivo YAML que define um processo (runner) de CI/CD.
- **Jobs**: Etapas que são executadas em um runner.
- **Steps**: Comandos individuais que são executados como parte de um job.

