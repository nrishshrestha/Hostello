/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import database.MySqlConnection;
import model.RoomData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class RoomDao {
    private MySqlConnection mySql = new MySqlConnection();

    public boolean addRoom(RoomData room) {
        String sql = "INSERT INTO rooms (room_number, room_type, room_status) VALUES (?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setString(3, room.getRoomStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in addRoom: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }    
    /**
     *
     * @param roomNumber
     * @return
     */
    public RoomData getRoomByNumber(String roomNumber) {
        String sql = "SELECT room_number,room_type,room_status FROM rooms WHERE room_number=?";
        Connection conn = mySql.openConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,roomNumber);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
            return new RoomData(
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getString("room_status")
            );
        }
        }catch (Exception e){
            System.err.println("Error retriving room: "+e.getMessage());
        }finally{
            mySql.closeConnection(conn);
        }
        return null;
    }
    

    public List<RoomData> getAllRooms() {
        String sql = "SELECT room_number, room_type, room_status FROM rooms";
        List<RoomData> rooms = new ArrayList<>();
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new RoomData(
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getString("room_status")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error in getAllRooms: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }
        return rooms;
    }

    public boolean updateRoom(RoomData room) {
        String sql = "UPDATE rooms SET room_type = ?, room_status = ? WHERE room_number = ?";
        Connection conn = mySql.openConnection();
        boolean result = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, room.getRoomType());
            stmt.setString(2, room.getRoomStatus());
            stmt.setString(3, room.getRoomNumber());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in updateRoom: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }
        return result;
    }

    public boolean deleteRoom(String roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_number = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in deleteRoom: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}