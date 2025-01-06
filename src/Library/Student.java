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
public class Student extends Item{
    private String course;
    private String branch;
    
    public Student(){
        
    }

    public Student(int studentId, String studentName, String course, String branch) {
        super.setItemId(studentId);
        super.setItemName(studentName);
        this.course = course;
        this.branch = branch;
    }

    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course){
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }
    
    public void setBranch(String branch){
        this.branch = branch;
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
                this.setItemId(rs.getInt("student_id"));
                this.setItemName(rs.getString("name"));
                this.setCourse(rs.getString("course"));
                this.setBranch(rs.getString("branch"));
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
