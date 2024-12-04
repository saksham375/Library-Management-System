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
 * Servlet for deleting a borrow record and updating book quantity.
 *
 * @author MSII
 */
public class DeleteBorrower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if the user is logged in and has admin privileges
        if (session.getAttribute("username") == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            resp.sendRedirect("Login");
            return;
        }

        try {
            // Retrieve book ID and borrower ID from the request
            String bookidStr = req.getParameter("bookid");
            String borrowerId = req.getParameter("id");

            int bookid = Integer.parseInt(bookidStr);

            // DAO instances
            BookDAO bookDAO = new BookDAO();
            BorrowerDAO borrowerDAO = new BorrowerDAO();

            // Update the book's quantity
            Book book = bookDAO.getBookById(bookid);
            if (book != null) {
                book.setCurrent(book.getCurrent() + 1);
                bookDAO.updateBook(book);
            }

            // Delete the borrower record
            borrowerDAO.deleteBorrower(borrowerId);

            // Redirect to the processing page
            resp.sendRedirect("ListBorrowAdmin?action=processing");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Invalid book ID or borrower ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Error deleting borrower");
        }
    }
}
