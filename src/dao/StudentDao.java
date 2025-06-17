/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */


import model.StudentData;
import database.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    MySqlConnection mySql = new MySqlConnection();

    // Add student
    public boolean addStudent(StudentData student) {
        String sql = "INSERT INTO students (name, email, phone, sex, age, occupation, institution, profile_picture, room_id, warden_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            stmt.setInt(10, student.getWardenId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch students for a specific warden
    public List<StudentData> getStudentsByWarden(int wardenId) {
        List<StudentData> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE warden_id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wardenId);
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
                student.setWardenId(rs.getInt("warden_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Update student
    public boolean updateStudent(StudentData student) {
        String sql = "UPDATE students SET name=?, email=?, phone=?, sex=?, age=?, occupation=?, institution=?, profile_picture=?, room_id=?, warden_id=? WHERE student_id=?";
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
            stmt.setInt(10, student.getWardenId());
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

