/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;

/**
 *
 * @author JOFREY
 */

public class Loan extends Item{
    private int studentId = 0;
    private String studentName =  null;
    private Date issueDate = null;
    private Date dueDate = null;
    private String status = null;

    public Loan(){
        
    }

    // Getters and Setters    
    public int getStudentId(){
        return this.studentId;
    }
    
    public void setStudentId(int studentId){
        this.studentId = studentId;
    }
    
    public String getStudentName(){
        return this.studentName;
    }
    
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    //insert issue book details to database
     public boolean issueBook(java.util.Date uIssueDate,java.util.Date uDueDate,int bookId,String bookName,int studentId,String studentName ){
         boolean isIssued = false;
         long l1 = uIssueDate.getTime();
         long l2 = uDueDate.getTime();  
         java.sql.Date sIssueDate = new java.sql.Date(l1);
         java.sql.Date sDueDate = new java.sql.Date(l2);
         
         try{
             Connection con = DBManager.getConnection();
             String sql = "insert into issue_book_details(book_id,book_name,id,"
                     + "student_name,issue_date,due_date,status) values(?,?,?,?,?,?,?)";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setInt(1,bookId);
             pst.setString(2,bookName);
             pst.setInt(3,studentId);
             pst.setString(4,studentName);
             pst.setDate(5, sIssueDate);
             pst.setDate(6, sDueDate);
             pst.setString(7, "pending");
             
             int rowCount = pst.executeUpdate();
             if(rowCount > 0){
                 isIssued = true;
             }else{
                 isIssued = false;
             }
         } catch(Exception e){
             e.printStackTrace();
         }
         return isIssued;
     }
     
     //checking whether book already allocated or not 
     public boolean isAlreadyIssued(String query, Object[] params) {
    boolean isAlreadyIssued = false;
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
        if (rs.next()) {
            isAlreadyIssued = true;
        } else {
            isAlreadyIssued = false;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isAlreadyIssued;
}

     
     public void setDataToCards(String query, JLabel label) {
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.last();
            label.setText(Integer.toString(rs.getRow()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
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
                this.setStudentId(rs.getInt("id"));
                this.setStudentName(rs.getString("student_name")); 
                this.setIssueDate(rs.getDate("issue_date")); 
                this.setDueDate(rs.getDate("due_date"));
                this.setStatus("status");
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
        // Handle the case when params and columnNames arrays are not compatible
        executed = false;
    }

    return executed;
} 
}
