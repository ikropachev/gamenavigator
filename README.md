# gamenavigator

(Frontend development in progress)

Web api for interacting with a database that stores data about video games.

* 2 types of users: admin and regular users
* Admin can input a game and genre
* Method to get a list of games of a certain genre
* Information about the game: name, developer studio, several genres that the game corresponds to

Tech stack: Maven, Spring MVC, Spring Security, Spring Data JPA, REST (Jackson), JDK17, <br>
Stream API, HSQLDB (in memory),  JUnit5, Swagger v2 (API).

The application is developed using TDD practice

Login information:
* admin@gmail.com:admin - sign in like admin
* user@gmail.com:user - sign in like regular user

[Swagger REST API](http://localhost:8080/swagger-ui.html)
