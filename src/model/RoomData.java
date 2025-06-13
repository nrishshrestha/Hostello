/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Acer
 */
public class RoomData {
    
    private int roomId;
    private String roomNo;
    private String roomType;
    private Float roomCost;
    private String roomStatus;
    private int userId;
    
    public RoomData(String roomNo, String roomType, Float roomCost, String roomStatus, int userId){
  
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.roomCost = roomCost;
        this.roomStatus = roomStatus;
        this.userId = userId;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomNo() {
        return roomNo;
    }
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
    
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public Float getRoomCost() {
        return roomCost;
    }
    public void setRoomCost(Float roomCost) {
        this.roomCost = roomCost;
    }
    
    public String getRoomStatus() {
        return roomStatus;
    }
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
