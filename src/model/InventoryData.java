/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class InventoryData {
    private int itemId;
    private String itemName;
    private int initialCount;
    private int ongoingCount;
    private String itemStatus; // 'remaining' or 'finished'
    private double costPerItem;
    private int userId; // the warden/admin who added it
    
    
    
        public InventoryData(String itemName, int initialCount, int ongoingCount, String itemStatus, double costPerItem, int userId) {
        this.itemName = itemName;
        this.initialCount = initialCount;
        this.ongoingCount = ongoingCount;
        this.itemStatus = itemStatus;
        this.costPerItem = costPerItem;
        this.userId = userId;
    }

    
        public InventoryData(int itemId, String itemName, int initialCount, int ongoingCount, String itemStatus, double costPerItem, int userId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.initialCount = initialCount;
        this.ongoingCount = ongoingCount;
        this.itemStatus = itemStatus;
        this.costPerItem = costPerItem;
        this.userId = userId;
    }

    
        public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getOngoingCount() {
        return ongoingCount;
    }

    public void setOngoingCount(int ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public double getCostPerItem() {
        return costPerItem;
    }

    public void setCostPerItem(double costPerItem) {
        this.costPerItem = costPerItem;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

    
    
    
    

