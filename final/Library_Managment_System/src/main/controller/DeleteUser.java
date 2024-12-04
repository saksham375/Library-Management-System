package controller;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for deleting a user. Accessible only to admin users.
 *
 * @author MSII
 */
public class DeleteUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if the user is logged in and has admin privileges
        if (session.getAttribute("username") == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            resp.sendRedirect("Login");
            return;
        }

        try {
            // Retrieve the username from the request
            String username = req.getParameter("id");

            if (username == null || username.isEmpty()) {
                throw new IllegalArgumentException("Username is required to delete a user.");
            }

            // Initialize UserDAO and delete the user
            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.deleteUser(username);

            if (!success) {
                throw new Exception("Failed to delete user. User may not exist.");
            }

            // Redirect to the user list after successful deletion
            resp.sendRedirect("ListUser");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Error deleting user.");
        }
    }
}
