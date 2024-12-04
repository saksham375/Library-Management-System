package DAO;

import dbConnect.DBContext;
import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object for managing Category entities.
 */
public class CategoryDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * Retrieves all categories as a map with category ID as the key and Category object as the value.
     *
     * @return a map of categories
     */
    public Map<Integer, Category> getMapCategory() {
        Map<Integer, Category> categoryMap = new HashMap<>();
        DBContext db = new DBContext();
        String sql = "SELECT * FROM `category`";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categoryMap.put(rs.getInt("id"), category);
            }
        } catch (Exception e) {
            System.out.println("getMapCategory: " + e.getMessage());
        } finally {
            closeResources();
        }
        return categoryMap;
    }

    /**
     * Retrieves all categories as a list.
     *
     * @return a list of categories
     */
    public ArrayList<Category> getListCategory() {
        ArrayList<Category> categoryList = new ArrayList<>();
        DBContext db = new DBContext();
        String sql = "SELECT * FROM `category`";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categoryList.add(category);
            }
        } catch (Exception e) {
            System.out.println("getListCategory: " + e.getMessage());
        } finally {
            closeResources();
        }
        return categoryList;
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
