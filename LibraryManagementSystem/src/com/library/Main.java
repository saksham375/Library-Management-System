package com.library; // Package declaration for the main class of the library system

import com.library.dao.BookDAO; // Importing BookDAO for book-related database operations
import com.library.model.Book; // Importing the Book model class
import com.library.util.DatabaseConnection; // Importing the DatabaseConnection utility class
import java.sql.Connection; // Importing the Connection interface for managing database connections

public class Main {
    // Main method - entry point of the application
    public static void main(String[] args) {
        System.out.println("Library Management System"); // Print the title of the application

        try {
            // Test database connection
            Connection conn = DatabaseConnection.getConnection(); // Get a connection to the database
            if (conn != null) { // Check if the connection was successful
                System.out.println("Database connection successful!"); // Print success message

                // Test adding a book
                BookDAO bookDAO = new BookDAO(); // Create an instance of BookDAO
                // Create a new Book object with details
                Book newBook = new Book("The  Gatsby", "F.  Fitzgerald", "9780743273123", 6);
                bookDAO.addBook(newBook); // Add the new book to the database
                System.out.println("Added new book with ID: " + newBook.getBookId()); // Print the ID of the newly added book

                // Test retrieving the book
                Book retrievedBook = bookDAO.getBookById(newBook.getBookId()); // Retrieve the book by its ID
                System.out.println("Retrieved book: " + retrievedBook); // Print the details of the retrieved book

                // Test getting all books
                System.out.println("\nAll books in database:"); // Print header for all books
                // Print all books in the database using forEach
                bookDAO.getAllBooks().forEach(System.out::println);

                DatabaseConnection.closeConnection(); // Close the database connection
            }
        } catch (Exception e) { // Catch any exceptions that occur
            System.out.println("Error occurred!"); // Print error message
            e.printStackTrace(); // Print the stack trace of the exception for debugging
        }
    }
}
