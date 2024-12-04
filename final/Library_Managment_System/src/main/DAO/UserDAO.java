package DAO;

import dbConnect.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Data Access Object for User operations.
 */
public class UserDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * Retrieves all users from the database, excluding those with a role set to true.
     *
     * @return a list of users
     */
    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement("SELECT * FROM USER");
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getBoolean("role"));
                user.setName(rs.getString("name"));
                user.setAvt(rs.getString("avt"));
                user.setSex(rs.getBoolean("sex"));
                user.setDatebirth(rs.getString("datebirth"));
                user.setPhone(rs.getString("phone"));
                user.setGmail(rs.getString("gmail"));
                if (!user.isRole()) {
                    users.add(user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in getAllUser: " + e.getMessage());
        } finally {
            closeResources();
        }
        return users;
    }

    /**
     * Finds a user by username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the user if found, null otherwise
     */
    public User findUser(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM USER WHERE username = ? AND password = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (Exception e) {
            System.out.println("Error in findUser: " + e.getMessage());
        } finally {
            closeResources();
        }
        return user;
    }

    /**
     * Finds a user by username.
     *
     * @param username the username of the user
     * @return the user if found, null otherwise
     */
    public User findUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM USER WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (Exception e) {
            System.out.println("Error in findUserByUsername: " + e.getMessage());
        } finally {
            closeResources();
        }
        return user;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the user to insert
     */
    public void insertUser(User user) {
        String sql = "INSERT INTO USER (username, password, role, name, avt, sex, datebirth, phone, gmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isRole());
            ps.setString(4, user.getName());
            ps.setString(5, user.getAvt() != null ? user.getAvt() : "img/avt/avt.jpg");
            ps.setBoolean(6, user.isSex());
            ps.setString(7, user.getDatebirth());
            ps.setString(8, user.getPhone());
            ps.setString(9, user.getGmail());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in insertUser: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to delete
     */
    public void deleteUser(String username) {
        String sql = "DELETE FROM USER WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in deleteUser: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Updates a user's information in the database.
     *
     * @param user the user with updated information
     */
    public void updateUser(User user) {
        String sql = "UPDATE USER SET password = ?, role = ?, name = ?, avt = ?, sex = ?, datebirth = ?, phone = ?, gmail = ? WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setBoolean(2, user.isRole());
            ps.setString(3, user.getName());
            ps.setString(4, user.getAvt());
            ps.setBoolean(5, user.isSex());
            ps.setString(6, user.getDatebirth());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getGmail());
            ps.setString(9, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in updateUser: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Maps a ResultSet row to a User object.
     *
     * @param rs the ResultSet
     * @return a User object
     * @throws Exception if mapping fails
     */
    private User mapUser(ResultSet rs) throws Exception {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getBoolean("role"));
        user.setName(rs.getString("name"));
        user.setAvt(rs.getString("avt"));
        user.setSex(rs.getBoolean("sex"));
        user.setDatebirth(rs.getString("datebirth"));
        user.setPhone(rs.getString("phone"));
        user.setGmail(rs.getString("gmail"));
        return user;
    }

    /**
     * Closes database resources (Connection, PreparedStatement, and ResultSet).
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
