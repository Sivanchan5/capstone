# 🐶 Dog Adoption Platform - Capstone Project

This project is a full-stack web application aimed at simplifying the dog adoption process by connecting shelters with potential adopters.
Built with **Spring Boot** and **Thymeleaf**, it supports user management, dog listings, adoption workflows, and administrative features.

---

## 🚀 Features
- 🏠 **Home Page**: View available dogs for adoption with search and filter options.
- 🔐 **User Authentication**: Secure user registration and login using Spring Security.
- 🛠️ **Admin Management**: Manage dogs, shelters, and users with admin privileges.
- 📂 **Image Uploads**: Upload and display images for dog profiles.
- 🔍 **Search by Breed**: Filter and search dogs by breed, age, and shelter.
- 📧 **Email Validation**: Validate email addresses during user registration.
- 📊 **RESTful API**: Manage dog data and adoptions through REST endpoints.

---

## 🏗️ Project Structure
capstone/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/capstone/
│   │   │   ├── config/             # Security and Web configurations
│   │   │   ├── controller/         # Controllers handling HTTP requests
│   │   │   ├── dto/                # Data Transfer Objects (DTOs)
│   │   │   ├── exception/          # Custom exception handling
│   │   │   ├── model/              # Entity models (JPA)
│   │   │   ├── repository/         # JPA repositories for database operations
│   │   │   ├── service/            # Business logic layer
│   │   │   └── validator/          # Input validation
│   │   └── resources/
│   │       ├── static/             # Static files (CSS, JS, images)
│   │       │   ├── css/            # Stylesheets
│   │       │   ├── js/             # JavaScript files
│   │       │   └── uploads/        # Uploaded images of dogs
│   │       ├── templates/          # Thymeleaf HTML templates
│   │       └── application.properties
│   └── test/                       # Unit and integration tests
│
├── target/                         # Compiled build artifacts
├── .gitignore                      # Files to exclude from Git
├── pom.xml                         # Maven build configuration
├── mvnw, mvnw.cmd                  # Maven wrapper scripts
├── LICENSE                         # Project license (MIT)
└── README.md                       # Project documentation (this file)

---

## 🛠️ Technologies Used
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Database**: MySQL
- **Testing**: JUnit, Mockito
- **Build Tool**: Maven

---

## ⚙️ Setup and Run
### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL (or H2 for development/testing)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Sivanchan5/capstone.git
   cd capstone

2.Configure the MySQL database connection:
Open src/main/resources/application.properties and add/modify the following:
# Database Configuration (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/capstone
spring.datasource.username=root
spring.datasource.password=12345678
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

3.Create the database in MySQL:
CREATE DATABASE capstone;
4.Build and run the application:
mvn spring-boot:run
5.Access the application at:
http://localhost:8080

🧪 Running Tests
To run unit and integration tests:
mvn test

## 📜 License
This project is licensed under the **MIT License**.  
See the [LICENSE](/LICENSE) file for more details.

📞 Contact
Author: Xiwen Chen
Email: sivanchan5@gmail.com
GitHub: github.com/Sivanchan5

