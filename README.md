# Cara-Suja API

![cara-suja](cara-suja.png)
> Periquito-cara-suja (Pyrrhura griseipectus)
>
> **Sobre**  
> Assim, como outros membros da família dos psitacídeos, os periquitos também formam casais para a vida toda. O
> Periquito-de-cara-suja é uma ave exclusivamente nordestina, atualmente ele ocorre em apenas 3 locais no estado do Ceara.
> É endêmica da Mata Atlântica. É uma espécie social que vive em bandos familiares de aproximadamente 4 a 15 indivíduos.
>
> **Ameaças**    
> Caça ilegal, tráfico de animais silvestres, destruição de ninhos e perca de habitat

## Start

Baixe/atualize o `code standard` para o projeto e adicione ao seu intellij.

```shell
sh scripts/getIntellijGoogleStyle.sh
```

## Arquitetura

```mermaid
---
title: Database
---

erDiagram
    users many(0) -- many(1) guests_users: "id-user_id and id-guest_id"
    users many(1) -- many(1) karteiras_users: id-user_id
    karteiras many(1) -- many(1) karteiras_users: id-karteira_id
    karteiras many(1) -- many(0) income: id-karteira_id
    karteiras many(1) -- many(0) invoice: id-karteira_id
    karteiras one to many accounts: id-karteira_id
    accounts one to one templates: template_id-id

    users {
        uuid id PK
        string email UK
        string password
        enum role
    }

    guests_users {
        uuid user_id FK "user id"
        uuid guest_id FK "user id"
    }

    karteiras {
        uuid id PK
        string name
        uint limit
        uint goal
    }

    karteiras_users {
        uuid user_id FK "user id"
        uuid karteira_id FK "karteira id"
    }

    accounts {
        uuid id PK
        string name
        uuid karteira_id FK
        uint template_id FK
    }

    templates {
        serial id PK
        enum category "details"
        enum frequency "payment"
        uint8 total_installments "payment"
        uint8 current_installment "payment"
        uint8 frequency_period "payment"
        bool is_recurrent "payment"
        timestampz payday "payment"
        timestampz last_payment "payment"
        string form "details"
        string method "details"
        string currency "price"
        number precision "price"
    }

    income {
        string id PK
        uuid karteira_id FK
        string name
        boolean has_no_end
        string frequency
        number period
        boolean is_finished
        number value
        number precision
        string account_id
        timestampz end_at
        timestampz received_at
        timestampz created_at
        timestampz updated_at
        timestampz deleted_at
    }

    invoice {
        string id
        string name
        number value
        string purchasedAt
        string form "detials"
        string method "detials"
        string source "detials"
        string category "detials"
        string frequency "details_payment"
        number frequencyPeriod "details_payment"
        number current "details_payment_installment"
        number total "details_payment_installment"
        boolean isRecurrent "details_payment"
        string payday "details_payment"
        string lastPayment "details_payment"
        number precision "details_price"
        string currency "detials_price"
        boolean isFinished
        string accountId
        string karteiraId
        string createdAt
        string updatedAt
        string deletedAt
        string[] payments
    }
```
