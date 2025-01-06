/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author JOFREY
 */
public class Book extends Item{
    private String author;
    private int quantity;
    
    public Book(){
        
    }
    public Book(int bookId, String bookName, String author, int quantity) {
        super.setItemId(bookId);
        super.setItemName(bookName);
        this.author = author;
        this.quantity = quantity;
    }

    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
     
    //Fetch item details from the item table in the database and display to item details panel
    @Override
public boolean getItemDetails(String query, Object[] params, String[] columnNames) {
    boolean executed = false;
    if (params != null && columnNames != null && query != null) {
        try (Connection con = DBManager.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            // Set the parameter values
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                this.setItemId(rs.getInt("book_id"));
                this.setItemName(rs.getString("book_name"));
                this.setAuthor(rs.getString("author"));
                this.setQuantity(rs.getInt("quantity"));
                executed = true;
            } else {
                executed = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exception
            executed = false;
        }
    } else {
       this.setItemId(0);
       this.setItemName(null);
       this.setAuthor(null);
       this.setQuantity(0);
       executed = false;
    }
    return executed;
} 

}
