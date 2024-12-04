package DAO;

import dbConnect.DBContext;
import entity.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Data Access Object for Feedback operations.
 */
public class FeedbackDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private DBContext db = new DBContext();

    /**
     * Retrieves a paginated list of feedback.
     *
     * @param list  the full list of feedback
     * @param start the starting index
     * @param end   the ending index
     * @return a sublist of feedback within the specified range
     */
    public ArrayList<Feedback> getListFeedbackByPage(ArrayList<Feedback> list, int start, int end) {
        ArrayList<Feedback> arr = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            arr.add(list.get(i));
        }
        return arr;
    }

    /**
     * Retrieves the list of all feedbacks from the database, ordered by ID in descending order.
     *
     * @return a list of feedback
     */
    public ArrayList<Feedback> getListFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY id DESC";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Feedback fb = new Feedback(rs.getString("title"), rs.getString("content"), rs.getString("user_id"));
                list.add(fb);
            }
        } catch (Exception e) {
            System.out.println("Error in getListFeedback: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Inserts a new feedback record into the database.
     *
     * @param fb the feedback to insert
     */
    public void insertFeedback(Feedback fb) {
        String sql = "INSERT INTO feedback (user_id, title, content) VALUES (?, ?, ?)";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, fb.getUsername());
            ps.setString(2, fb.getTitle());
            ps.setString(3, fb.getConnent());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in insertFeedback: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Closes database resources (Connection, PreparedStatement, and ResultSet) to prevent resource leaks.
     */
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
