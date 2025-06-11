/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
//This class will carry data from the view (GUI) to the controller and DAO.
public class UserData {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String ProfilePicture;

    public UserData(String username, String email, String password, String role, String ProfilePicture) {
        
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ProfilePicture= ProfilePicture;
    }
    public UserData(int user_id,String username, String email, String password, String role, String ProfilePicture) {
        this.user_id=user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ProfilePicture= ProfilePicture;
    }

    public int getUserId() {
        return user_id;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void ProfilePicture(String ProfilePicture){
        this.ProfilePicture= ProfilePicture;
    }
  }

