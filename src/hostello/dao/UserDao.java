/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.dao;

/**
 *
 * @author ACER
 */
import hostello.database.MySqlConnection;
import hostello.model.UserData;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import hostello.model.ResetPasswordRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
 public UserData getUserById(int userId) {
    
String sql = "SELECT * FROM users WHERE user_id = ?";
  Connection conn = mySql.openConnection();
    try  {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int user_id=rs.getInt("user_id");
                String username = rs.getString("username");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String role = rs.getString("role");
            // Set other fields as needed
            return new UserData(user_id,username, userEmail, userPassword, role);
        }
        else {
                return null; // no user found
            }

    } catch (Exception e) {
        e.printStackTrace();
   return null;}
         finally {
            mySql.closeConnection(conn);
        }
 }
 
 
   public boolean deleteUser(int userId) {
    String query = "DELETE FROM users WHERE user_id = ?";
    Connection conn = mySql.openConnection();
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeUpdate() > 0;
    } catch(Exception e){
        e.printStackTrace();
        return false;
    }
}

   
   public boolean updateUser(UserData user) {
    String sql = "UPDATE users SET username = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
    Connection conn = mySql.openConnection();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getRole());
        stmt.setInt(5, user.getUserId());
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        mySql.closeConnection(conn);
    }
}

public boolean addAdmin(UserData user) {
    String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 'admin')";
    Connection conn = mySql.openConnection();

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());

        int result = stmt.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        System.out.println("Error in addAdmin: " + e.getMessage());
        return false;
    } finally {
        mySql.closeConnection(conn);
    }
}


   
 public List<UserData> getAllAdmins() {
    List<UserData> admins = new ArrayList<>();
    String sql = "SELECT * FROM users WHERE role = 'admin'";
    Connection conn = mySql.openConnection();

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UserData admin = new UserData(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
            );
            admins.add(admin);
        }

    } catch (SQLException e) {
        System.out.println("Error in getAllAdmins: " + e.getMessage());
    } finally {
        mySql.closeConnection(conn);
    }

    return admins;
}

   
public List<UserData> getAllWardens() {
    List<UserData> wardens = new ArrayList<>();
    String sql = "SELECT * FROM users WHERE role = 'warden'";
    Connection conn = mySql.openConnection();

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UserData warden = new UserData(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
            );
            wardens.add(warden);
        }

    } catch (SQLException e) {
        System.out.println("Error in getAllWardens: " + e.getMessage());
    } finally {
        mySql.closeConnection(conn);
    }

    return wardens;
}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
    
    public UserData login(String email, String password) {
        String query = "SELECT user_id, username, email, password, role FROM users WHERE email = ? AND password = ?";
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
