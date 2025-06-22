import dao.InventoryDao;
import model.InventoryData;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.List;

public class InventoryDaoTest {
    private InventoryDao inventoryDao;
    private InventoryData testItem;

    @Before
    public void setUp() {
        inventoryDao = new InventoryDao();
        testItem = new InventoryData("TestItem", 10, 5, "Available", 100.0, 1);
        inventoryDao.addInventory(testItem);
    }

    @After
    public void tearDown() {
        List<InventoryData> items = inventoryDao.getInventoryByUser(1);
        for (InventoryData item : items) {
            if ("TestItem".equals(item.getItemName())) {
                inventoryDao.deleteInventory(item.getItemId());
            }
        }
    }

    @Test
    public void testAddInventory() {
        assertTrue(inventoryDao.addInventory(new InventoryData("AnotherItem", 5, 2, "Available", 50.0, 1)));
    }

    @Test
    public void testGetInventoryByUser() {
        List<InventoryData> items = inventoryDao.getInventoryByUser(1);
        assertFalse(items.isEmpty());
    }

    @Test
    public void testUpdateInventory() {
        List<InventoryData> items = inventoryDao.getInventoryByUser(1);
        InventoryData item = items.get(0);
        item.setItemName("UpdatedItem");
        assertTrue(inventoryDao.updateInventory(item));
    }

    @Test
    public void testDeleteInventory() {
        InventoryData item = new InventoryData("DeleteMe", 1, 1, "Available", 10.0, 1);
        inventoryDao.addInventory(item);
        List<InventoryData> items = inventoryDao.getInventoryByUser(1);
        int idToDelete = -1;
        for (InventoryData i : items) {
            if ("DeleteMe".equals(i.getItemName())) {
                idToDelete = i.getItemId();
            }
        }
        assertTrue(inventoryDao.deleteInventory(idToDelete));
    }
}