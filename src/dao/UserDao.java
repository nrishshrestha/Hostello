/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */
import database.MySqlConnection;
import model.UserData;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.ResetPasswordRequest;

public class UserDao {

    MySqlConnection mySql = new MySqlConnection();

    public boolean register(UserData user) {
        String query = "INSERT INTO users(username, email, password, role) VALUES (?, ?, ?, 'warden')";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            stmnt.setString(2, user.getEmail());
            stmnt.setString(3, user.getPassword());

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            System.out.println("Error in register: " + e.getMessage());
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean checkEmail(String email){
        String query="Select * from users where email=?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            
            stmnt.setString(1, email);
            

            ResultSet result = stmnt.executeQuery();
            if(result.next()){
                return true;
            } else{
                return false;
            }
            

        } catch (Exception e) {
            
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
        
    }
    
    public boolean resetPassword(ResetPasswordRequest resetReq){
//Step 1: write a string query
String query ="UPDATE users SET password=? where email=?";
Connection conn =mySql.openConnection();
try{
PreparedStatement stmt =conn.prepareStatement(query);
stmt.setString(1,resetReq.getPassword());
stmt.setString(2, resetReq.getEmail());

int result =stmt.executeUpdate();
return result>0;
}catch(Exception e){
return false;
}finally{
mySql.closeConnection(conn);
}
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    public UserData login(String email, String password) {
        String query = "SELECT username, email, password, role FROM users WHERE email = ? AND password = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Get data from result
                int user_id=rs.getInt("user_id");
                String username = rs.getString("username");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String role = rs.getString("role");
                

                return new UserData(user_id,username, userEmail, userPassword, role);
                
                
            } else {
                return null; // no user found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
