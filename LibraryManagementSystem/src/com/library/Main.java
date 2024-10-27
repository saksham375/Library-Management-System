package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Library Management System");

        try {
            // Test database connection
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");

                // Test adding a book
                BookDAO bookDAO = new BookDAO();
                Book newBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 5);
                bookDAO.addBook(newBook);
                System.out.println("Added new book with ID: " + newBook.getBookId());

                // Test retrieving the book
                Book retrievedBook = bookDAO.getBookById(newBook.getBookId());
                System.out.println("Retrieved book: " + retrievedBook);

                // Test getting all books
                System.out.println("\nAll books in database:");
                bookDAO.getAllBooks().forEach(System.out::println);

                DatabaseConnection.closeConnection();
            }
        } catch (Exception e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}