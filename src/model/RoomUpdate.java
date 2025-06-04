/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Acer
 */
public class RoomUpdate {
    
    private String roomNo;
    private String roomType;
    private Float roomCost;
    public RoomUpdate(String roomNo,String roomType,Float roomCost){
        this.roomNo=roomNo;
        this.roomType= roomType;
        this.roomCost=roomCost;
    }
    public void setRoomNo(String roomNo){
        this.roomNo=roomNo;
        
    }
    public void setRoomType(String roomType){
        this.roomType=roomType;
    }
    public void setRoomCost(Float roomCost){
        this.roomCost=roomCost;
    }
    
    public String getRoomNo(){
        return roomNo;
    }
    public String getRoomType(){
        return roomType;
    }
    public Float getRoomCost(){
        return roomCost;
    }

}
