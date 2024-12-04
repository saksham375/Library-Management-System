package controller;

import DAO.BookDAO;
import DAO.BorrowerDAO;
import entity.Book;
import entity.Borrower;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Servlet for completing borrower actions.
 *
 * @author MSII
 */
public class CompleteBorrower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Ensure the user is logged in and is an admin
        if (session.getAttribute("username") == null || session.getAttribute("role") == null
                || !session.getAttribute("role").equals("admin")) {
            resp.sendRedirect("Login");
            return;
        }

        try {
            // Get book ID and parse it
            String bookIdStr = req.getParameter("bookid");
            int bookId = Integer.parseInt(bookIdStr);

            // Update book quantity
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);
            if (book != null && book.getCurrent() > 0) {
                book.setCurrent(book.getCurrent() - 1);
                bookDAO.updateBook(book);
            } else {
                resp.sendRedirect("errorPage.jsp?message=Invalid Book ID or Book is not available");
                return;
            }

            // Get borrower ID
            String id = req.getParameter("id");

            // Update borrower details
            BorrowerDAO borrowerDAO = new BorrowerDAO();
            Borrower borrower = borrowerDAO.getBorrowerById(id);

            if (borrower != null) {
                borrower.setStatus("Borrowed");

                // Set borrow date and return date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String borrowFrom = dateFormat.format(new Date());
                borrower.setBorrow_from(borrowFrom);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 14); // Set return date to 14 days later
                String borrowTo = dateFormat.format(calendar.getTime());
                borrower.setBorrow_to(borrowTo);

                // Update borrower in database
                borrowerDAO.updateBorrower(borrower);
                resp.sendRedirect("ListBorrowAdmin?action=processing");
            } else {
                resp.sendRedirect("errorPage.jsp?message=Borrower record not found");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("errorPage.jsp?message=Invalid Book ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=An error occurred while processing the request");
        }
    }
}
