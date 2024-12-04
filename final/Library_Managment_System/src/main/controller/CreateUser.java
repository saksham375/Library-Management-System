package controller;

import DAO.UserDAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for creating new users.
 *
 * @author MSII
 */
public class CreateUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward to the user creation page
        req.getRequestDispatcher("CreateUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Retrieve form parameters
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String passwordConfirm = req.getParameter("passwordConfirm");
            String name = req.getParameter("name");
            boolean sex = Boolean.parseBoolean(req.getParameter("sex"));
            String datebirth = req.getParameter("datebirth");
            String phone = req.getParameter("phone");
            String gmail = req.getParameter("gmail");

            // Initialize UserDAO
            UserDAO udao = new UserDAO();

            // Check if the username already exists
            User existingUser = udao.findUserByUsername(username);

            // Validate password confirmation
            if (!password.equals(passwordConfirm)) {
                req.setAttribute("message", "*Password and Password Confirm do not match");
                setRequestAttributesForRetry(req, username, name, sex, datebirth, phone, gmail);
                req.getRequestDispatcher("CreateUser.jsp").forward(req, resp);
                return;
            }

            // Validate username uniqueness
            if (existingUser != null) {
                req.setAttribute("message", "*Username already exists");
                setRequestAttributesForRetry(req, username, name, sex, datebirth, phone, gmail);
                req.getRequestDispatcher("CreateUser.jsp").forward(req, resp);
                return;
            }

            // Create a new User object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setName(name);
            newUser.setSex(sex);
            newUser.setDatebirth(datebirth);
            newUser.setPhone(phone);
            newUser.setGmail(gmail);
            newUser.setRole(false); // Default role is non-admin

            // Insert the new user into the database
            udao.insertUser(newUser);

            // Redirect to the user list page after successful creation
            resp.sendRedirect("ListUser");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("errorPage.jsp?message=Error creating user");
        }
    }

    /**
     * Sets the attributes for retrying user creation in case of validation failure.
     */
    private void setRequestAttributesForRetry(HttpServletRequest req, String username, String name,
                                              boolean sex, String datebirth, String phone, String gmail) {
        User retryUser = new User();
        retryUser.setUsername(username);
        retryUser.setName(name);
        retryUser.setSex(sex);
        retryUser.setDatebirth(datebirth);
        retryUser.setPhone(phone);
        retryUser.setGmail(gmail);

        req.setAttribute("user", retryUser);
    }
}
