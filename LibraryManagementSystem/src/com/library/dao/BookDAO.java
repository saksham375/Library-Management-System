// BookDAO.java
package com.library.dao; // Package declaration for the Data Access Object layer of the library system

import com.library.model.Book; // Importing the Book model class
import com.library.util.DatabaseConnection; // Importing the utility class for database connection
import java.sql.*; // Importing SQL classes for database operations
import java.util.ArrayList; // Importing ArrayList for storing a list of books
import java.util.List; // Importing List interface for list operations

public class BookDAO { // Class responsible for database operations related to books

    // Method to add a new book to the database
    public void addBook(Book book) throws SQLException {
        // SQL query to insert a new book into the books table
        String sql = "INSERT INTO books (title, author, isbn, quantity, available_quantity) VALUES (?, ?, ?, ?, ?)";

        // Using try-with-resources to ensure proper resource management
        try (Connection conn = DatabaseConnection.getConnection(); // Get a database connection
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Prepare the SQL statement with generated keys

            // Setting the values of the prepared statement using the Book object's properties
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setInt(5, book.getAvailableQuantity());

            // Execute the insert operation
            pstmt.executeUpdate();

            // Retrieve the generated keys (i.e., the book ID) after insertion
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1)); // Set the generated book ID in the Book object
                }
            }
        }
    }

    // Method to retrieve a book by its ID
    public Book getBookById(int bookId) throws SQLException {
        // SQL query to select a book based on its ID
        String sql = "SELECT * FROM books WHERE book_id = ?";

        // Using try-with-resources for resource management
        try (Connection conn = DatabaseConnection.getConnection(); // Get a database connection
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement

            pstmt.setInt(1, bookId); // Set the book ID in the prepared statement

            // Execute the query and process the results
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // Check if a result was returned
                    // Create a new Book object and populate it with data from the result set
                    Book book = new Book(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getInt("quantity")
                    );
                    // Set additional properties
                    book.setBookId(rs.getInt("book_id"));
                    book.setAvailableQuantity(rs.getInt("available_quantity"));
                    return book; // Return the populated Book object
                }
            }
        }
        return null; // Return null if no book was found
    }

    // Method to retrieve all books from the database
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>(); // List to hold all books
        String sql = "SELECT * FROM books"; // SQL query to select all books

        // Using try-with-resources for resource management
        try (Connection conn = DatabaseConnection.getConnection(); // Get a database connection
             Statement stmt = conn.createStatement(); // Create a statement object
             ResultSet rs = stmt.executeQuery(sql)) { // Execute the query

            // Loop through the result set and create Book objects
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("quantity")
                );
                // Set additional properties
                book.setBookId(rs.getInt("book_id"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                books.add(book); // Add the Book object to the list
            }
        }
        return books; // Return the list of books
    }
}
