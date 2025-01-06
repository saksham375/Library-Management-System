/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JOFREY
 */
public abstract class Item {
    protected int itemId;
    protected String itemName;
    
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
     public boolean addItemData(String query, Object[] params){
        boolean isAdded = false;

        try{
            Connection con = DBManager.getConnection();
            PreparedStatement pst = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }

            int rowCount = pst.executeUpdate();

            if(rowCount > 0){
                isAdded = true;
            }else{
                isAdded = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isAdded;
    }
     
     
    public boolean updateItemData(String query, Object[] params){
        boolean isUpdated = false;

        try{
            Connection con = DBManager.getConnection();
            PreparedStatement pst = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }

            int rowCount = pst.executeUpdate();
            if(rowCount > 0){
                isUpdated = true;

            }else{
                isUpdated = false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isUpdated;
    }
    
    
    public boolean deleteItemData(String query, Object[] params){
        boolean isDeleted = false;

        try {
            Connection con = DBManager.getConnection();
            PreparedStatement pst = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }

            int rowCount = pst.executeUpdate();
            if(rowCount > 0){
                isDeleted = true;
            }else{
                isDeleted = false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;
    }
    
    // updating book count
     public boolean updateItemCount(String query, Object[] params){
         boolean isDeleted = false;
        try {
            Connection con = DBManager.getConnection();
            PreparedStatement pst = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }

            int rowCount = pst.executeUpdate();
            if(rowCount > 0){
                isDeleted = true;
            }else{
                isDeleted = false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;
     }
     
    public abstract boolean getItemDetails(String query, Object[] params, String[] columnNames);

    // Fetch item details into a model
    public boolean setItemDetailsToTable(DefaultTableModel model, String query, Object[] params, String[] columnNames) {
        boolean executed = false;
        if (this.getItemId() != 0) {
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
                executed = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return executed;
    }
     // set student details into the table
     public void setItemDetailsToTable(JTable table, String query, Object[] params, String[] columnNames) {
     DBManager.setTableData((DefaultTableModel) table.getModel(), table, query, params, columnNames);
    } 
}

