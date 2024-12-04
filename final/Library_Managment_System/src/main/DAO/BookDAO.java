package DAO;

import dbConnect.DBContext;
import entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * BookDAO class for handling database operations related to the Book entity.
 */
public class BookDAO {

    // Database connection and query execution objects
    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    /**
     * Paginates a list of books by returning a sublist from the given start and end indices.
     *
     * @param list  the original list of books
     * @param start the starting index (inclusive)
     * @param end   the ending index (exclusive)
     * @return a sublist of books
     */
    public ArrayList<Book> getListBookByPage(ArrayList<Book> list, int start, int end) {
        ArrayList<Book> paginatedList = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            paginatedList.add(list.get(i));
        }
        return paginatedList;
    }

    /**
     * Retrieves all books from the database and returns them in a Map with book IDs as keys.
     *
     * @return a Map of books, or null if an error occurs
     */
    public Map<Integer, Book> getMapBook() {
        Map<Integer, Book> bookMap = new HashMap<>();
        String sql = "SELECT * FROM book";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute query
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            rs = pt.executeQuery();

            // Process the result set
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("category"),
                        rs.getString("publisher"),
                        rs.getString("language"),
                        rs.getInt("total"),
                        rs.getInt("current"),
                        rs.getString("position")
                );
                bookMap.put(book.getBookid(), book);
            }
        } catch (Exception ex) {
            System.out.println("Error fetching book map: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return bookMap;
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of books, or null if an error occurs
     */
    public ArrayList<Book> getAllBook() {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute query
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            rs = pt.executeQuery();

            // Process the result set
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("category"),
                        rs.getString("publisher"),
                        rs.getString("language"),
                        rs.getInt("total"),
                        rs.getInt("current"),
                        rs.getString("position")
                );
                bookList.add(book);
            }
        } catch (Exception ex) {
            System.out.println("Error fetching all books: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return bookList;
    }

    /**
     * Searches for books by name.
     *
     * @param name the search term for book names
     * @return a list of books matching the search term
     */
    public ArrayList<Book> getBookByName(String name) {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE name LIKE ?";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute query
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            pt.setString(1, "%" + name + "%");
            rs = pt.executeQuery();

            // Process the result set
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("category"),
                        rs.getString("publisher"),
                        rs.getString("language"),
                        rs.getInt("total"),
                        rs.getInt("current"),
                        rs.getString("position")
                );
                bookList.add(book);
            }
        } catch (Exception ex) {
            System.out.println("Error fetching books by name: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return bookList;
    }

    /**
     * Retrieves books by their category ID.
     *
     * @param categoryId the category ID
     * @return a list of books in the given category
     */
    public ArrayList<Book> getBookByCategory(int categoryId) {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE category = ?";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute query
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            pt.setInt(1, categoryId);
            rs = pt.executeQuery();

            // Process the result set
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("category"),
                        rs.getString("publisher"),
                        rs.getString("language"),
                        rs.getInt("total"),
                        rs.getInt("current"),
                        rs.getString("position")
                );
                bookList.add(book);
            }
        } catch (Exception ex) {
            System.out.println("Error fetching books by category: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return bookList;
    }

    /**
     * Retrieves a single book by its ID.
     *
     * @param id the book ID
     * @return the book with the given ID, or null if not found
     */
    public Book getBookById(int id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute query
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            pt.setInt(1, id);
            rs = pt.executeQuery();

            // Process the result set
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("category"),
                        rs.getString("publisher"),
                        rs.getString("language"),
                        rs.getInt("total"),
                        rs.getInt("current"),
                        rs.getString("position")
                );
            }
        } catch (Exception ex) {
            System.out.println("Error fetching book by ID: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * Inserts a new book into the database.
     *
     * @param book the book object to insert
     */
    public void insertBook(Book book) {
        String sql = "INSERT INTO book (id, name, author, category, publisher, language, total, current, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBContext db = new DBContext();

        try {
            // Establish connection and execute insert
            con = db.getConnection();
            pt = con.prepareStatement(sql);
            pt.setInt(1, book.getBookid());
            pt.setString(2, book.getName());
            pt.setString(3, book.getAuthor());
            pt.setInt(4, book.getCategory());
            pt.setString(5, book.getPublisher());
            pt.setString(6, book.getLanguage());
            pt.setInt(7, book.getTotal());
            pt.setInt(8, book.getCurrent());
            pt.setString(9, book.getPosition());
            pt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error inserting book: " + ex.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Releases database resources (Connection, PreparedStatement, ResultSet).
     */
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pt != null) pt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
