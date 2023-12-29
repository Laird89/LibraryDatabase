# LibraryDatabase

This was an assignment for my third semester Java class.

**1. Project Overview:**
   - The project is a web application designed for a library database, allowing users to view, add, and review books.
   - It employs the Spring Boot framework, integrating Spring MVC for web functionality, Thymeleaf for templating, and Spring Security for access control.

**2. Bean Classes:**
   - **Book:**
     - Represents a book entity with properties such as ID, title, author, and a list of reviews.
   - The `Book` class is a fundamental structure for book-related operations in the application.

   - **ErrorMessage:**
     - Provides a standardized format for error messages, particularly for handling exceptions in the application.

   - **Review:**
     - Represents a review entity associated with a book, containing information like ID, book ID, and review text.

   - **SiteUser:**
     - Represents a site user with properties including ID, username, password, and user role.
     - The user roles are predefined as "ADMIN" and "USER."

**3. Controllers:**
   - **HomeController:**
     - Manages the home page and handles various requests related to books.
     - Displays a list of books, allows users to add reviews, and facilitates user registration.
     - Secures certain routes based on user roles, providing an "Add Book" functionality accessible only to administrators.

   - **BookController:**
     - Handles requests related to books, including retrieving book lists, getting details for a specific book, and adding new books.
     - Integrates with the `DatabaseAccess` class to interact with the underlying database.

**4. Database Access:**
   - **DatabaseAccess:**
     - Interacts with the database using Spring's `NamedParameterJdbcTemplate`.
     - Provides methods for retrieving books, adding books, getting reviews for a book, and adding reviews.
     - Establishes foreign key relationships between the `books` and `reviews` tables.

**5. Security Configuration:**
   - **SecurityConfig:**
     - Configures Spring Security to manage access control and authentication.
     - Defines rules for URL access based on user roles (e.g., only admins can access the "Add Book" page).
     - Utilizes a custom access denied handler to log denied access attempts.

**6. HTML Templates:**
   - A set of HTML templates provides the user interface for various aspects of the application, such as login, registration, book viewing, review addition, permission denial, and book addition (for administrators).

**7. SQL Schema:**
   - The `schema.sql` file defines the database schema, creating tables for books and reviews with appropriate relationships.
   - Inserts sample data to illustrate the functionality of the application.

**8. Additional Features:**
   - User registration and login functionality, with password encryption using BCrypt.
   - Role-based access control, restricting certain actions to users with specific roles.
   - Integration with Spring Security for secure authentication and authorization.

This project follows best practices for web application development, incorporating Spring Boot features to streamline development and enhance security. It offers a robust foundation for a library management system with user authentication, role-based access control, and database interaction.
