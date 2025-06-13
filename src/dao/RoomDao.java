/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.RoomData;
import database.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.MySqlConnection;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoomDao {
MySqlConnection mySql = new MySqlConnection();
    // Create a new room record

// Fetch available rooms only (status = 'available')
public List<String> getAvailableRoomNumbersByWarden(int userId) {
    List<String> roomNos = new ArrayList<>();
    String sql = "SELECT room_no FROM rooms WHERE room_status = 'available' AND user_id = ?";
    Connection conn = mySql.openConnection();
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            roomNos.add(rs.getString("room_no"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return roomNos;
}

    public boolean addRoom(RoomData room) {
        String sql = "INSERT INTO rooms (room_no, room_type, room_cost, room_status, user_id) VALUES (?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
        
            stmt.setString(1, room.getRoomNo());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getRoomCost());
            stmt.setString(4, room.getRoomStatus());
            stmt.setInt(5, room.getUserId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all rooms for a given warden (user_id)
    public List<RoomData> getRoomsByWarden(int userId) {
        List<RoomData> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE user_id = ?";
        Connection conn = mySql.openConnection();
        try {
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoomData room = new RoomData();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNo(rs.getString("room_no"));
                room.setRoomType(rs.getString("room_type"));
                room.setRoomCost(rs.getDouble("room_cost"));
                room.setRoomStatus(rs.getString("room_status"));
                room.setUserId(rs.getInt("user_id"));

                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Update existing room
    public boolean updateRoom(RoomData room) {
        String sql = "UPDATE rooms SET room_no = ?, room_type = ?, room_cost = ?, room_status = ?, user_id = ? WHERE room_id = ?";
        Connection conn = mySql.openConnection();
      try {
             PreparedStatement stmt = conn.prepareStatement(sql);
             
            stmt.setString(1, room.getRoomNo());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getRoomCost());
            stmt.setString(4, room.getRoomStatus());
            stmt.setInt(5, room.getUserId());
            stmt.setInt(6, room.getRoomId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a room by ID
    public boolean deleteRoom(String roomNo) {
        String sql = "DELETE FROM rooms WHERE room_no = ?";
       Connection conn = mySql.openConnection(); 
       try {
             PreparedStatement stmt = conn.prepareStatement(sql);
             
            stmt.setString(1, roomNo);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
