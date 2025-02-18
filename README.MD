# User Service with JWT and OAuth2 Authentication

This project is a Spring Boot application that provides user management functionalities, including:

- **User Registration:** Create new user accounts.
- **User Authentication:** Authenticate users using username and password, issuing a JWT upon successful login.
- **Request Filtering:** Applies a custom filter to validate JWTs on incoming requests and enforce role-based access control on all subsequent requests
- **OAuth2 Authentication:** Enable users to authenticate via GitHub, creates a user and generating a JWT upon successful OAuth2 login and redirecting to a `/post-login` endpoint.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Usage Guidelines](#usage-guidelines)
- [Configuration](#configuration)
    - [Database](#database)
    - [JWT](#jwt)
    - [OAuth2 with GitHub](#oauth2-with-github)
- [Running the Application](#running-the-application)
- [Usage](#usage)
    - [User Registration](#user-registration)
    - [User Login](#user-login)
    - [Accessing Protected Endpoints](#accessing-protected-endpoints)
    - [OAuth2 Login via GitHub](#oauth2-login-via-github)
- [Custom JWT Filter](#custom-jwt-filter)
- [Role-Based Access Control](#role-based-access-control)
- [Troubleshooting](#troubleshooting)
- [References](#references)
- [License](#license)

## Features

- **User Registration and Authentication:** Securely register and authenticate users using Spring Security 6.
- **JWT Integration:** Issue and validate JWTs for stateless authentication.
- **Custom Security Filter:** Intercept and validate JWTs on all incoming requests.
- **Role-Based Authorization:** Restrict access to endpoints based on user roles and privileges.
- **OAuth2 Integration with GitHub:** Allow users to authenticate using their GitHub accounts.

## Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- **MySQL 8.x Database**
- **Babbangona-commons-library**
- **API Gateway(request routing)**

## Setup Instructions

1. **Clone the Repository:**
   git clone https://github.com/4thworldsoldier/user-management-service.git

2. **Build and package the Babbangona-commons-library:**
   ensure to clone the dependent project lib from
   git clone https://github.com/4thworldsoldier/babbangona-commons-library.git
   Using any IDE/editor that supports java build and package the commons-lib as a jar file into your /.m2 folder

3. **Create an empty database schema:**
   using workbench or any other mysql cient, run the below sql script 
   ** CREATE DATABASE usermanager;

4. **update database schema credentials:**
   Update the application.properties with your database credentials and uncomment this #spring.jpa.hibernate.ddl-auto=create remember to comment out once service is running 

5. **Run the project:**
   Using any IDE/editor that supports java build and run the project, this will enable hibernate ORM provider create the necessary table structure for the service.

6. **Seeding the database:**
   run the MYSQLSCRIPT.TXT file in the root directory of this project, this will create the necessary tenants,roles and privileges needed to test the service

## Usage Guidelines
**Note:**
postman collection contains all sample test data/endpoints

- **Register Flow:**
    - Clients send Registraion requests to `/api/auth/login/internal/user/register` to register a user. see postman collection for sample requests.

- **Login Flow:**
    - Clients send login requests to `/api/auth/login` on the User Management Service (routed via the API Gateway).
    - On successful authentication, a JWT token is returned.
    - This token must be included in the `Authorization` header (`Bearer <token>`) for subsequent requests to secured endpoints.

- **Token Validation:**
    - services use a JWT authorization filter that intercepts incoming requests (except for unsecured routes like `/api/auth/**`)  to validate the token.
    - If the token is invalid or missing, the service returns a JSON response with error details.

- **GitHub OAuth2 Integration:**
    - When a user opts to login via GitHub, the User Management Service receives the OAuth2 flow via spring security's internal integration with OAuth2 providers like github.
    - The API Gateway intercepts the OAuth2 callback and routes it to the User Management Service OnSuccessHandler.
    - After processing (user registration and token generation), the User Management Service redirects the user (via the API Gateway) to a configured post-login endpoint where a jwt token has been generated for the user, for subsequent use.