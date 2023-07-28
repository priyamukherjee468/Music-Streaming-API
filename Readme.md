# Music Streaming API
_______


## Framework Used
* Spring Boot
----
## Dependencies
The following dependencies are required to run the project:


* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
* Swagger
 ----
## Language used
* Java (Version: 17)
----

## Data Model

<br>

* User Model
```
Id : integer
password : string
email : string
role : Role

```

* Playlist Model
```
playlistId : Long
playListName: String
songs : songs
User : user
```
* Song Model
```
SongId : Long
SongTitle : String
Artist : String 
user: User
```


* Authentication Token
```
tokenId : Long
tokenValue : string
tokenCreationDateTime : LocalDate
@OneToOne 
user : User
```

* Role 
```
Enum: ADMIN, 
      NORMAL

```

## Data Flow

1. The user at client side sends a request to the application through the API endpoints.
2. The API receives the request and sends it to the appropriate controller method.
3. The controller method makes a call to the method in service class.
4. The method in service class builds logic and retrieves or modifies data from the database, which is in turn given to controller class
5. The controller method returns a response to the API.
6. The API sends the response back to the user.

---

## DataBase Used
* SQL database
```

We have used Persistent database to implement CRUD Operations.
```
---
## Documentation

implement a Music Streaming application using Spring Boot. The code you shared includes several controller classes (PlayListController, UserController, SongController) and other supporting classes (DTOs, models, repositories, services).

Here is a breakdown of the code you provided:

1. `PlayListController` class: This class is a Spring RestController that handles HTTP requests related to playlists. 
2. `SongController` class: This class is a Spring RestController that handles HTTP requests related to songs. It has methods for getting all songs.
3. `UserController` class: This class is a Spring RestController that handles HTTP requests related to user. It has a method for making CRUD Operations for song,playlist.In this controller User can Sign In,Signup and SignOut successfully.
4. `DTOs (Data Transfer Objects)`: These classes (`SignInInput`, `SignInOutput`, `SignUpInput`, `SignUpOutput`) represent the data transferred between the client and server. They contain fields and annotations for data validation.
5. `Models`: These classes (`AuthenticationToken`, `PlayList`, `Role`, `Song`, `User`) represent the entities in your application. They are annotated with JPA annotations to define the database schema.
6. `Repositories`: These interfaces (`IPlayListRepo`, `ISongRepo`, `IAuthTokenRepo`, `IUserRepo`) extend the Spring Data JPA `JpaRepository` interface and provide methods for interacting with the database.
7. `Services`: These classes (`PlayListService`, `UserService`, `SongService`, `AuthenticationService`, `UserService`) contain the business logic of your application. They use the repositories to perform CRUD operations on the entities.


   ##### Overall, the code structure follows the MVC (Model-View-Controller) pattern commonly used in Spring Boot applications. The controllers handle HTTP requests, the services handle the business logic, and the repositories handle the database operations.