/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

/**
 *
 * @author JOFREY
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DBManager {
    
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/library_ms";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    static Connection con = null;
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    
    public static void setTableData(DefaultTableModel model, JTable table, String query, Object[] params, String[] columnNames) {
        try {
            Connection con = DBManager.getConnection();
            PreparedStatement pst = con.prepareStatement(query);

            // Set parameters for the query
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    String columnName = columnNames[i];
                    Object value = rs.getObject(columnName);
                    rowData[i] = value;
                }

                model.addRow(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static boolean verifyPassword(String enteredPassword, String storedPassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedPassword);
    }
    
    public static void clearLabels(JLabel... labels) {
        for (JLabel label : labels) {
            label.setText("");
        }
    }
}

