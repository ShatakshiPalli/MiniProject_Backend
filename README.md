# EDUBLOG Backend

A modern, scalable backend for the EduBlog platform, built with Java and Spring Boot.

---

## 🚀 Features
- RESTful API endpoints for content management
- User authentication and authorization
- Scalable architecture ready for deployment
- Robust data handling and business logic

## 🔧 Tech Stack

- **Java + Spring Boot** – Backend framework and language
- **MongoDB** – NoSQL database for storing user data, blog posts, and interactions
- **JWT (JSON Web Tokens)** – Stateless authentication and authorization
- **Spring Security** – Secure API access management
- **Spring Data MongoDB** – ORM-style data access for MongoDB
- **Maven** – Build automation and dependency management


## 📋 Prerequisites
Before running this project, make sure you have:
- JDK 11 or higher
- Maven 3.2 or higher
- Git

## 🔧 Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/ShatakshiPalli/MiniProject_Backend.git
   cd MiniProject_Backend
   ```
2. **Build the project:**
    This command compiles the source code and packages it into a `.jar` file.
   ```bash
   mvn clean install
   ```
3. **Configure the application:**
   Open `src/main/resources/application.properties` and update the database connection details and other environment-specific settings.
   
4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   The backend API will be available at `http://localhost:8080` (or the configured port).

## 🏗️ Project Structure
```
MiniProject_Backend/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/edublog/          # Source code
│   │   │   ├── config/                # Spring configuration
│   │   │   ├── controller/            # API controllers
│   │   │   ├── model/                 # Data models (Entities)
│   │   │   ├── repository/            # Data repositories
│   │   │   └── service/               # Business logic
│   │   └── resources/                 # Application properties, static assets
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml                            # Project dependencies and plugins
```

## 🚀 Available Scripts
- `mvn clean install` – Compiles, tests, and packages the application.
- `mvn spring-boot:run` – Runs the application using the Spring Boot Maven plugin.
- `mvn test` – Runs the unit tests for the project.

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License
This project is licensed under the MIT License.

## 👥 Authors
- SHATAKSHI PALLI
- ACHA SRIDATTA VARMA
- MEGHANA BAJJURI
- MOHAMMED ABBU HUZAIFA

## 🙏 Acknowledgments
- Spring Boot team
- Maven community
- All contributors and supporters
