/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class RoomData {
    private int room_id;
    private String room_no;
    private String room_type;
    private double room_cost;
    private String room_status;
    private int user_id; // warden_id
    
     public RoomData() {}
     
     public RoomData(String room_no, String room_type, double room_cost, String room_status, int user_id){
         this.room_no= room_no;
         this.room_type= room_type;
         this.room_cost= room_cost;
         this.room_status= room_status;
         this.user_id= user_id;
     }
     
     public RoomData (int room_id){
         this.room_id= room_id;
     }

    
}
