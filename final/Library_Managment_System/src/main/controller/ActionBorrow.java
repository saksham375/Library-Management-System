package controller;

import DAO.BookDAO;
import DAO.BorrowerDAO;
import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for managing borrow actions.
 *
 * @author MSII
 */
public class ActionBorrow extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if the user is logged in
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
            return;
        }

        String url = req.getParameter("url"); // URL to redirect after borrowing
        String bookId = req.getParameter("id"); // Book ID passed as a parameter

        try {
            // Retrieve and update book details
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(Integer.parseInt(bookId));

            if (book != null && book.getCurrent() > 0) {
                book.setCurrent(book.getCurrent() - 1);
                bookDAO.updateBook(book);

                // Insert borrow record into the database
                BorrowerDAO borrowerDAO = new BorrowerDAO();
                String username = session.getAttribute("username").toString();
                borrowerDAO.insertBorrower(username, bookId);

                // Redirect to the provided URL
                resp.sendRedirect(url);
            } else {
                // Handle case where the book is not available
                resp.sendRedirect("errorPage.jsp?message=Book is not available");
            }
        } catch (Exception e) {
            // Log exception and redirect to an error page
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=An error occurred while borrowing the book");
        }
    }
}
