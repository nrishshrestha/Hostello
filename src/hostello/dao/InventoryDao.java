/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.dao;

/**
 *
 * @author ACER
 */
import hostello.model.InventoryData;
import hostello.database.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao {
     MySqlConnection mySql = new MySqlConnection();
     
     
     
         public boolean addInventory(InventoryData item) {
        String sql = "INSERT INTO inventory (item_name, initial_count, ongoing_count, item_status, cost_per_item, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getInitialCount());
            stmt.setInt(3, item.getOngoingCount());
            stmt.setString(4, item.getItemStatus());
            stmt.setDouble(5, item.getCostPerItem());
            stmt.setInt(6, item.getUserId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
         public List<InventoryData> getInventoryByUser(int userId) {
        List<InventoryData> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE user_id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InventoryData item = new InventoryData(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("initial_count"),
                    rs.getInt("ongoing_count"),
                    rs.getString("item_status"),
                    rs.getDouble("cost_per_item"),
                    rs.getInt("user_id")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

     
     
         public boolean updateInventory(InventoryData item) {
        String sql = "UPDATE inventory SET item_name = ?, initial_count = ?, ongoing_count = ?, item_status = ?, cost_per_item = ?, user_id = ? WHERE item_id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getInitialCount());
            stmt.setInt(3, item.getOngoingCount());
            stmt.setString(4, item.getItemStatus());
            stmt.setDouble(5, item.getCostPerItem());
            stmt.setInt(6, item.getUserId());
            stmt.setInt(7, item.getItemId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
         public boolean deleteInventory(int itemId) {
        String sql = "DELETE FROM inventory WHERE item_id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

     
     
     
     

