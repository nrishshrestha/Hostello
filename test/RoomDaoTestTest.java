/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.Test;
import static org.junit.Assert.*;
import dao.RoomDao;
import model.RoomData;
import org.junit.*;
import java.util.List;


/**
 *
 * @author User
 */
public class RoomDaoTestTest {
     private static RoomDao roomDao;
    private static RoomData testRoom;
     @BeforeClass
    public static void setUpBeforeClass() {
        roomDao = new RoomDao();
        testRoom = new RoomData();
        testRoom.setRoomNo("101");
        testRoom.setRoomType("Single");
        testRoom.setRoomCost(5000.0);
        testRoom.setRoomStatus("available");
        testRoom.setUserId(1);
    }
      @Test
    public void testAddRoom() {
        boolean result = roomDao.addRoom(testRoom);
        assertTrue("Room should be added successfully", result);
    }
     @Test
    public void testGetAvailableRoomNumbersByWarden() {
        List<String> availableRooms = roomDao.getAvailableRoomNumbersByWarden(1);
        assertNotNull("Available rooms list should not be null", availableRooms);
        assertTrue("Should contain room 101", availableRooms.contains("101"));
    }
    @Test
    public void testGetRoomsByWarden() {
        List<RoomData> rooms = roomDao.getRoomsByWarden(1);
        assertNotNull("Room list should not be null", rooms);
        assertTrue("Should contain at least 1 room", rooms.size()>0);
    }
     @Test
    public void testUpdateRoom() {
        testRoom.setRoomType("Double");
        testRoom.setRoomCost(6500.0);

      
        List<RoomData> rooms = roomDao.getRoomsByWarden(1);
        for (RoomData r : rooms) {
            if (r.getRoomNo().equals("101")) {
                testRoom.setRoomId(r.getRoomId());
                break;
            }
        }

        boolean result = roomDao.updateRoom(testRoom);
        assertTrue("Room should be updated", result);
    }
    
    @Test
    public void testDeleteRoom() {
        boolean result = roomDao.deleteRoom("101");
        assertTrue("Room should be deleted", result);
    }
    @AfterClass
    public static void tearDownAfterClass() {
       
        roomDao.deleteRoom("101");
    }


    
   
    
}
