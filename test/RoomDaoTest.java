import dao.RoomDao;
import model.RoomData;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.List;

public class RoomDaoTest {
    private RoomDao roomDao;
    private RoomData testRoom;

    @Before
    public void setUp() {
        roomDao = new RoomDao();
        testRoom = new RoomData(0, "101", "Single", 500.0, "available", 1);
        roomDao.addRoom(testRoom);
    }

    @After
    public void tearDown() {
        List<RoomData> rooms = roomDao.getRoomsByWarden(1);
        for (RoomData room : rooms) {
            if ("101".equals(room.getRoomNo())) {
                roomDao.deleteRoom(room.getRoomNo());
            }
        }
    }

    @Test
    public void testAddRoom() {
        assertTrue(roomDao.addRoom(new RoomData(0, "102", "Double", 700.0, "available", 1)));
    }

    @Test
    public void testGetRoomsByWarden() {
        List<RoomData> rooms = roomDao.getRoomsByWarden(1);
        assertFalse(rooms.isEmpty());
    }

    @Test
    public void testUpdateRoom() {
        List<RoomData> rooms = roomDao.getRoomsByWarden(1);
        RoomData room = rooms.get(0);
        room.setRoomType("UpdatedType");
        assertTrue(roomDao.updateRoom(room));
    }

    @Test
    public void testDeleteRoom() {
        RoomData room = new RoomData(0, "103", "Single", 400.0, "available", 1);
        roomDao.addRoom(room);
        assertTrue(roomDao.deleteRoom("103"));
    }
}