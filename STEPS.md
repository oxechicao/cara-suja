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
