
# Library Management System

This is a Spring Boot application for managing a book library, including CRUD operations for books, authors, and book rentals. The application uses MongoDB as the database to store data.



## Setup Instructions
#### Prerequisites
- Java Development Kit (JDK) installed on your machine
- MongoDB installed and running locally or accessible from your environment
- IDE (IntelliJ IDEA recommended) or text editor of your choice
#### Steps
- Clone or download the repository to your local machine.
- Open the project in your IDE.
- Configure the MongoDB connection settings in application.properties file located in src/main/resources directory.
- Build and run the application.


## Running the Application
Once the application is running, you can interact with it using RESTful endpoints. Below are the endpoints and sample requests to test them:
### Endpoints
#### Books
```http
GET /api/book/getAllbooks
```
 Description: Retrieve all books.
 ```http
 GET /api/book/getBookByIsbn/{isbn}
```
Description: Retrieve book by Id
```http
POST /api/book/addBook/{authorId}
```
Description: Create a new book.
Jason
    {
    "title": "The Great Gatsby",
    "authorId": "1",
    "isbn": "978-3-16-148410-0",
    "publicationYear": 1925
    }

```http
 PUT /api/book/updateBook/{isbn}
```

- Description: Update an existing book.
Jason
   {
    "title": "Updated Title",
    "isbn": "978-3-16-148410-0",
    "publicationYear": 2021
  }

```http
 DELETE /api/book/removeBook/{isbn}
```

 Remove book

#### Author
```http
 POST /api/author/addAuthor
```
- Description: Creates a new author.
Jason {
    "name": "Author Name",
    "biography": "Author Biography"
   }
```http
  PUT /api/author/updateAuthor?id={author_id} 
```
- Description : Updates an existing author with the specified ID.

RequestBody
 {
  "name": "Updated Author Name",
  "biography": "Updated Biography"
}

```http
 GET /api/author/getAuthorById/{author_id}
```
Description: Retrieves the author with the specified ID.
```http
GET /api/author/getAllAuthor
```
Retrieves all authors.
```http
DELETE /api/author/removeAuthor/{author_id}
```
Description: Deletes the author with the specified ID.

#### Rental
```http
POST /api/rental/rentBook
```
Description: Rents a book.

Request Body: 
{
  "bookId": "book_id",
  "renterName": "Renter Name",
  "rentalDate": "yyyy-MM-ddTHH:mm:ss",
  "returnDate": "yyyy-MM-ddTHH:mm:ss"
}

```http
GET /api/rental/bookAvailableForRent/{book_id}
```
Description: Checks if a book is available for rent.

```http
GET /api/rental/getAllRentalRecords
```
Description: Retrieves all rental records.

```http
GET /api/rental/checkOverdueRentals/{rental_id}
```
Description: Checks for overdue rentals.


## Swagger Documentation
This application utilizes Swagger for API documentation. Swagger UI provides a user-friendly interface to interact with the RESTful APIs and understand their functionality. Follow the steps below to access the Swagger documentation:

- Ensure that the application is running locally or deployed to a server accessible from your browser.
- Open your web browser and navigate to the following URL:
```http
http://localhost:8080/swagger-ui.html
```
- You should now see the Swagger UI interface displaying a list of available endpoints categorized by controller. Click on any controller to expand its endpoints and view detailed information, including request and response schemas.
- Explore the endpoints by clicking on them, and use the "Try it out" feature to execute requests directly from the browser.

## Additional Documentation
- This application uses Spring Boot and MongoDB for efficient data management and scalability.
- All API endpoints follow RESTful principles and return appropriate HTTP status codes for successful or failed requests.
- Input validation is implemented to ensure that required fields are provided and values are in the correct format.
- Proper error handling is implemented for invalid requests or unexpected errors.
- Integration tests can be added to ensure that endpoints work as expected with the database.
