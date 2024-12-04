package controller;

import DAO.BookDAO;
import DAO.CategoryDAO;
import entity.Book;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet to handle book creation.
 *
 * @author MSII
 */
public class CreateBook extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if the user is logged in and is an admin
        if (session.getAttribute("username") == null || session.getAttribute("role") == null
                || !session.getAttribute("role").equals("admin")) {
            resp.sendRedirect("Login");
            return;
        }

        // Fetch the list of categories for book creation
        try {
            CategoryDAO cDAO = new CategoryDAO();
            ArrayList<Category> list = cDAO.getListCategory();
            req.setAttribute("list", list);
            req.getRequestDispatcher("CreateBook.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Error fetching categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if the user is logged in and is an admin
        if (session.getAttribute("username") == null || session.getAttribute("role") == null
                || !session.getAttribute("role").equals("admin")) {
            resp.sendRedirect("Login");
            return;
        }

        try {
            // Parse book details from the request
            int bookId = Integer.parseInt(req.getParameter("bookid"));
            String name = req.getParameter("name");
            String author = req.getParameter("author");
            int categoryId = Integer.parseInt(req.getParameter("category"));
            String publisher = req.getParameter("publisher");
            String language = req.getParameter("language");
            int total = Integer.parseInt(req.getParameter("total"));
            int current = Integer.parseInt(req.getParameter("current"));
            String position = req.getParameter("position");

            // Create a new Book object
            Book newBook = new Book(bookId, name, author, categoryId, publisher, language, total, current, position);
            BookDAO bDAO = new BookDAO();

            // Check if a book with the same ID already exists
            if (bDAO.getBookById(bookId) != null) {
                CategoryDAO cDAO = new CategoryDAO();
                ArrayList<Category> list = cDAO.getListCategory();

                req.setAttribute("list", list);
                req.setAttribute("book", newBook);
                req.setAttribute("mess", "Book ID already exists");
                req.getRequestDispatcher("CreateBook.jsp").forward(req, resp);
                return;
            }

            // Insert the new book into the database
            bDAO.insertBook(newBook);
            resp.sendRedirect("ListBook");
        } catch (NumberFormatException e) {
            req.setAttribute("mess", "Invalid input. Please check your data.");
            req.getRequestDispatcher("CreateBook.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Error creating book");
        }
    }
}
