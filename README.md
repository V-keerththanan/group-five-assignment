# Eplanet Ecommerce Backend
This is the backend for the Eplanet ecommerce website. It is built with Spring Boot and provides the necessary APIs for the frontend to interact with the backend.
## Getting Started
### To get started with the project, follow these steps:
1. Clone the repository to your local machine
2. Open the project in your preferred IDE
3. Run the application
4. Navigate to "http://localhost:8080" in your browser to confirm the application is running

### Technologies Used
. Spring Boot
. Spring Security
. MySQL
. Hibernate
. JWT

### API Endpoints
The following endpoints are available in the backend:

1. /auth/register - User registration
2. /auth/sign-in - User login
3. /product/** - CRUD operations for products
4. /order/** -CRUD operations for orders
5. /cart/**  - CRUD operations for cart
6. /checkout - to make a checkout process
7. /user/** - CRUD operations for user

### Authentication
The backend uses JWT for authentication. Upon successful login, a token is generated and sent to the client in the response header. This token must be included in all subsequent requests to protected endpoints.

### Database Configuration
The backend uses MySQL for persistent storage. The configuration for the database can be found in application.properties
