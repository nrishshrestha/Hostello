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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import hostello.model.StudentData;

public class StudentDao {
    MySqlConnection mySql = new MySqlConnection();

    // Add student
    public boolean addStudent(StudentData student) {
        String sql = "INSERT INTO students (name, email, phone, sex, age, occupation, institution, profile_picture, room_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getSex());
            stmt.setInt(5, student.getAge());
            stmt.setString(6, student.getOccupation());
            stmt.setString(7, student.getInstitution());
            stmt.setString(8, student.getProfilePicture());
            stmt.setInt(9, student.getRoomId());
            stmt.setInt(10, student.getUserId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public StudentData getStudentById(int studentId) {
      StudentData student = null;
      String sql = "SELECT * FROM students s JOIN rooms r ON s.room_id = r.room_id WHERE s.student_id = ?";
      Connection conn = mySql.openConnection();
      try {
           PreparedStatement stmt = conn.prepareStatement(sql);
  
          stmt.setInt(1, studentId);
          ResultSet rs = stmt.executeQuery();
  
          if (rs.next()) {
              student = new StudentData();
              student.setStudentId(rs.getInt("student_id"));
              student.setName(rs.getString("name"));
              student.setEmail(rs.getString("email"));
              student.setPhone(rs.getString("phone"));
              student.setSex(rs.getString("sex"));
              student.setAge(rs.getInt("age"));
              student.setOccupation(rs.getString("occupation"));
              student.setInstitution(rs.getString("institution"));
              student.setProfilePicture(rs.getString("profile_picture"));
              student.setRoomId(rs.getInt("room_id"));
              student.setRoomNo(rs.getString("room_no")); // Add this field to StudentData class
          }
  
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
      return student;
  }
  
    
    
    
    // Fetch students for a specific warden
   public List<StudentData> getStudentsByWarden(int user_id) {
    List<StudentData> students = new ArrayList<>();
    String sql = "SELECT s.*, r.room_no FROM students s " +
                 "JOIN rooms r ON s.room_id = r.room_id " +
                 "WHERE s.user_id = ?";

    Connection conn = mySql.openConnection();
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, user_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            StudentData student = new StudentData();
            student.setStudentId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setSex(rs.getString("sex"));
            student.setAge(rs.getInt("age"));
            student.setOccupation(rs.getString("occupation"));
            student.setInstitution(rs.getString("institution"));
            student.setProfilePicture(rs.getString("profile_picture"));
            student.setRoomId(rs.getInt("room_id"));
            student.setUserId(rs.getInt("user_id"));

            // 🔥 Here's the fix
            student.setRoomNo(rs.getString("room_no"));

            students.add(student);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return students;
}


    // Update student
    public boolean updateStudent(StudentData student) {
        String sql = "UPDATE students SET name=?, email=?, phone=?, sex=?, age=?, occupation=?, institution=?, profile_picture=?, room_id=?, user_id=? WHERE student_id=?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getSex());
            stmt.setInt(5, student.getAge());
            stmt.setString(6, student.getOccupation());
            stmt.setString(7, student.getInstitution());
            stmt.setString(8, student.getProfilePicture());
            stmt.setInt(9, student.getRoomId());
            stmt.setInt(10, student.getUserId());
            stmt.setInt(11, student.getStudentId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete student by ID
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

