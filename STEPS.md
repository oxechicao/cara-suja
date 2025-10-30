# Step by step

<!-- TOC -->

* [Step by step](#step-by-step)
    * [Get spring boot initialize](#get-spring-boot-initialize)
    * [Setup Mongodb connection for localhost](#setup-mongodb-connection-for-localhost)

<!-- TOC -->

This project was developed through TDD approach, so for everything here, some test ran before.

## Get spring boot initialize

1. Access the [quickstart](https://spring.io/quickstart) and download the spring boot start kit.
2. Unzip the code and open in any editor, for example Intellij community.

## Setup Mongodb connection for localhost

1. Open `src/resources/application.properties`
2. Add this following settings for localhost

```properties
spring.data.mongodb.uri=mongodb://localhost:27017
spring.data.mongodb.database=karteiradb
```

## Writing Controller tests

The controller tests on this project don't test the entire workflow. It should be unit test as much as possible.
The only goal of the controller is:

1. validate the request input
2. convert the request input into domain model
3. catch the errors correctly
4. response correctly

### UserController

1. Create a test to create the route.
    1. The test should just verify if the route exists by checking the status code OK.
