import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Entity: Feedback
class Feedback {
    private String title;
    private String content;
    private String username;

    public Feedback(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }
}

// Entity: Category
class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

// Database Utility
class DBUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "5560";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

// DAO: Category
class CategoryDAO {
    public ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection()) {
            String query = "SELECT * FROM categories";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

// DAO: Feedback
class FeedbackDAO {
    public void insertFeedback(Feedback feedback) {
        try (Connection conn = DBUtils.getConnection()) {
            String query = "INSERT INTO feedback (title, content, username) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, feedback.getTitle());
            ps.setString(2, feedback.getContent());
            ps.setString(3, feedback.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Servlet: FeedbackUserServlet
public class FeedbackUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
            return;
        }

        // Fetch categories for sidebar
        CategoryDAO cadao = new CategoryDAO();
        ArrayList<Category> listca = cadao.getListCategory();
        req.setAttribute("listca", listca);
        req.getRequestDispatcher("Feedback.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
            return;
        }

        // Insert feedback
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String username = session.getAttribute("username").toString();

        Feedback fb = new Feedback(title, content, username);
        FeedbackDAO fdao = new FeedbackDAO();
        fdao.insertFeedback(fb);

        resp.sendRedirect("Feedback");
    }
}
