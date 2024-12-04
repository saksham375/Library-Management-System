package DAO;

import dbConnect.DBContext;
import entity.Borrower;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * BorrowerDAO handles all database operations related to Borrower entities.
 */
public class BorrowerDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * Paginates a list of borrowers by returning a sublist from the given start and end indices.
     *
     * @param list  the original list of borrowers
     * @param start the starting index (inclusive)
     * @param end   the ending index (exclusive)
     * @return a sublist of borrowers
     */
    public ArrayList<Borrower> getListBorrowerByPage(ArrayList<Borrower> list, int start, int end) {
        ArrayList<Borrower> paginatedList = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            paginatedList.add(list.get(i));
        }
        return paginatedList;
    }

    /**
     * Deletes a borrower by ID.
     *
     * @param id the borrower's ID
     */
    public void deleteBorrower(String id) {
        String sql = "DELETE FROM `borrower` WHERE `id` = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteBorrower: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Retrieves borrowers by status and username.
     *
     * @param status   the borrower's status
     * @param username the username of the borrower
     * @return a list of borrowers
     */
    public ArrayList<Borrower> getBorrowerByStatusAndUsername(String status, String username) {
        ArrayList<Borrower> list = new ArrayList<>();
        String sql = "SELECT * FROM `borrower` WHERE `status` = ? AND `username` = ?";
        DBContext db = new DBContext();

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                Borrower borrower = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
                if (!status.equals("processing")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date toDate = dateFormat.parse(rs.getString("to"));
                    Date currentDate = new Date();
                    if (!toDate.after(currentDate)) {
                        borrower.setLate(true);
                    }
                }
                list.add(borrower);
            }
        } catch (Exception e) {
            System.out.println("getBorrowerByStatusAndUsername: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Retrieves borrowers by status.
     *
     * @param status the borrower's status
     * @return a list of borrowers
     */
    public ArrayList<Borrower> getBorrowerByStatus(String status) {
        ArrayList<Borrower> list = new ArrayList<>();
        String sql = "SELECT * FROM `borrower` WHERE `status` = ?";
        DBContext db = new DBContext();

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                Borrower borrower = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
                if (!status.equals("processing")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date toDate = dateFormat.parse(rs.getString("to"));
                    Date currentDate = new Date();
                    if (!toDate.after(currentDate)) {
                        borrower.setLate(true);
                    }
                }
                list.add(borrower);
            }
        } catch (Exception e) {
            System.out.println("getBorrowerByStatus: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Retrieves a borrower by ID.
     *
     * @param id the borrower's ID
     * @return the borrower object
     */
    public Borrower getBorrowerById(String id) {
        String sql = "SELECT * FROM `borrower` WHERE `id` = ?";
        DBContext db = new DBContext();
        Borrower borrower = null;

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                borrower = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            System.out.println("getBorrowerById: " + e.getMessage());
        } finally {
            closeResources();
        }
        return borrower;
    }

    /**
     * Updates a borrower in the database.
     *
     * @param borrower the borrower object to update
     */
    public void updateBorrower(Borrower borrower) {
        String sql = "UPDATE `borrower` SET `username` = ?, `book_id` = ?, `form` = ?, `to` = ?, `status` = ? WHERE `id` = ?";
        DBContext db = new DBContext();

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, borrower.getUsername());
            ps.setInt(2, borrower.getBookid());
            ps.setString(3, borrower.getBorrow_from());
            ps.setString(4, borrower.getBorrow_to());
            ps.setString(5, borrower.getStatus());
            ps.setInt(6, borrower.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateBorrower: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Inserts a new borrower into the database.
     *
     * @param username the username of the borrower
     * @param bookId   the ID of the borrowed book
     */
    public void insertBorrower(String username, String bookId) {
        String sql = "INSERT INTO `borrower` (`username`, `book_id`, `status`) VALUES (?, ?, 'processing')";
        DBContext db = new DBContext();

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insertBorrower: " + e.getMessage());
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
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
