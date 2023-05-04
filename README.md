# Test Atmos
# Project Support
### Installation Guide
* Clone this repository [here](https://github.com/Muhammadyusufpr/test_atmos.git).
### Usage
* Connect to the API using Postman on port 8080.
### API Endpoints
| HTTP Verbs | Endpoints | Action | Access
| --- | --- | --- | ---
| POST | /api/v1/auth/register | To sign up a new user account | ALL
| POST | /api/v1/auth/login | To login an existing user account | ALL
| GET | /api/v1/city/cities-list | To get all city list | ALL
| PUT | /api/v1/city/edit-city/{{id}} | To edit an existing city | ADMIN
| PUT | /api/v1/city/update-city-weather/{{id}} | To edit city weather status | ADMIN
| POST | /api/v1/profile-city/subscribe-to-city/{{cityId}} | To subscribe an existing city | USER
| GET | /api/v1/profile-city/get-subscriptions | To get subscriptions which user subscribe to city | USER
| GET | /api/v1/profile-city/profile-details | To get user subscription details | ADMIN
| GET | /api/v1/profile/profile-list |  To get all profile list | ADMIN
| GET | /api/v1/profile/edit-profile/{{id}} |  To edit an existing profile | ADMIN


### Technologies Used
* [Java](https://www.java.com/ru/) 
* [Postgres](https://www.postgresql.org/) 
* [Spring Web Flux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
* [Liqiubase](https://www.liquibase.org/) 
* [JWT](https://jwt.io/) 
