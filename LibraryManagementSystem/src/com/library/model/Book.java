// Book.java
package com.library.model; // Package declaration for the model class representing a book

public class Book {
    // Instance variables to hold book properties
    private int bookId; // Unique identifier for the book
    private String title; // Title of the book
    private String author; // Author of the book
    private String isbn; // International Standard Book Number for the book
    private int quantity; // Total quantity of the book available in the library
    private int availableQuantity; // Quantity of the book currently available for borrowing

    // Constructor to initialize a new Book object
    public Book(String title, String author, String isbn, int quantity) {
        this.title = title; // Set the title
        this.author = author; // Set the author
        this.isbn = isbn; // Set the ISBN
        this.quantity = quantity; // Set the total quantity
        this.availableQuantity = quantity; // Set the available quantity to total quantity initially
    }

    // Getters and Setters for the instance variables

    public int getBookId() {
        return bookId; // Return the book ID
    }

    public void setBookId(int bookId) {
        this.bookId = bookId; // Set the book ID
    }

    public String getTitle() {
        return title; // Return the title
    }

    public void setTitle(String title) {
        this.title = title; // Set the title
    }

    public String getAuthor() {
        return author; // Return the author
    }

    public void setAuthor(String author) {
        this.author = author; // Set the author
    }

    public String getIsbn() {
        return isbn; // Return the ISBN
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn; // Set the ISBN
    }

    public int getQuantity() {
        return quantity; // Return the total quantity
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; // Set the total quantity
    }

    public int getAvailableQuantity() {
        return availableQuantity; // Return the available quantity
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity; // Set the available quantity
    }

    // Override toString method for a readable representation of the Book object
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId + // Include book ID
                ", title='" + title + '\'' + // Include title
                ", author='" + author + '\'' + // Include author
                ", isbn='" + isbn + '\'' + // Include ISBN
                ", quantity=" + quantity + // Include total quantity
                ", availableQuantity=" + availableQuantity + // Include available quantity
                '}'; // End of the string representation
    }
}
