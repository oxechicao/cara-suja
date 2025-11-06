# Step by step

<!-- TOC -->
* [Step by step](#step-by-step)
  * [Get spring boot initialize](#get-spring-boot-initialize)
  * [Setup Mongodb connection for localhost](#setup-mongodb-connection-for-localhost)
  * [Writing Features](#writing-features)
    * [Controllers](#controllers)
    * [Service](#service)
    * [Repository](#repository)
  * [Features](#features)
    * [Create/Register new user](#createregister-new-user)
      * [User Controller layer](#user-controller-layer)
      * [User Service layer](#user-service-layer)
      * [User Repository layer](#user-repository-layer)
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

## Writing Features

### Controllers

The controller tests on this project don't test the entire workflow. It should be unit test as much
as possible.
The only goal of the controller is:

1. validate the request input
2. convert the request input into domain model
3. catch the errors correctly
4. response correctly

### Service

1. Should verify that calls all external functions correctly
2. Should check if the response are respecting the contract
3. Should test all throw exceptions cases

### Repository

Should do integration test if possible.

## Features

### Create/Register new user

#### User Controller layer

**1. Test if the body is empty and return 400.**

Do not send body content to the route, it should return 400 because they don't receive any body
content to validate. The
message should be `invalid body content`

```sh
curl -X POST http://localhost:8080/api/v1/users
```

**2. Test if the body content is valid.**

Should test if the fields are valid or not.

| **field** | **validations**                                          |
|----------:|:---------------------------------------------------------|
|      name | not empty                                                |
|           | only alphanumeric, blank space` `, quote `'`, hyphen `-` |
|  password | not empty                                                |
|           | minimum six (6) characters                               |
|     email | not empty                                                |
|           | valid e-mail                                             |
| serielKey | not empty                                                |

**3. Test route success and return 201.**

The test should just verify if the route exists by checking the status code OK.

```sh
curl -X POST http://localhost:8080/api/v1/users
  -H "Content-type: application/json"
  -d '{ "name": "Ahsoka Tano", "email": "ahsoka.tano@jedi.temple", "password": "best-jedi", "serialKey": "serial-key-for-testing" }'
```

**4. Call the service with the valid request.**

The test should just call the service, mocking the successful response

#### User Service layer

**1. Call the repository with the UserEntity**

The test should verify that you call correctly the persistence layer with the correct Object.

**2. Check if the response is the ID returned by the save operation.**

Just check if the returned value is the ID returned by save operation.

#### User Repository layer

Do integration test here.

**1. Save the user entity in the database.**

The test should verify if the user entity is correctly saved in the database.

**2. Check if the returned value is the ID of the saved entity.**

The test should verify if the returned value is the ID of the saved entity.