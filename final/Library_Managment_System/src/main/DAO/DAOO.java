package DAO;

import dbConnect.DBContext;
import entity.TopBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Data Access Object for handling top books and users.
 */
public class DAOO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private DBContext db = new DBContext();

    /**
     * Retrieves the top 5 most borrowed books.
     *
     * @return a list of top books
     */
    public ArrayList<TopBook> getTopBook() {
        ArrayList<TopBook> list = new ArrayList<>();
        String sql = "SELECT b.name, A.Total FROM ("
                + "SELECT book_id, COUNT(book_id) AS Total "
                + "FROM borrower "
                + "WHERE status != 'processing' "
                + "GROUP BY book_id "
                + "ORDER BY Total DESC LIMIT 5) A "
                + "JOIN book b ON A.book_id = b.id";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                TopBook tb = new TopBook(rs.getString("name"), rs.getInt("Total"));
                list.add(tb);
            }
        } catch (Exception e) {
            System.out.println("getTopBook: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Retrieves the top 5 users who borrowed the most books.
     *
     * @return a list of top users
     */
    public ArrayList<TopBook> getTopUser() {
        ArrayList<TopBook> list = new ArrayList<>();
        String sql = "SELECT username, COUNT(username) AS Total "
                + "FROM borrower "
                + "WHERE status != 'processing' "
                + "GROUP BY username "
                + "ORDER BY Total DESC LIMIT 5";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                TopBook tb = new TopBook(rs.getString("username"), rs.getInt("Total"));
                list.add(tb);
            }
        } catch (Exception e) {
            System.out.println("getTopUser: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
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
