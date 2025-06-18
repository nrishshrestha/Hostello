/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author DSGAVS
 */
import dao.InventoryDao;
import dao.UserDao;
import model.InventoryData;
import model.UserData;
import view.InventoryView;
import view.WardenDashboardView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InventoryController {
    private final InventoryView view;
    private final InventoryDao dao;
    private final int currentUserId;

    public InventoryController(InventoryView view, int userId) {
        this.view = view;
        this.dao = new InventoryDao();
        this.currentUserId = userId;

        // Initialize view components
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setResizable(false);

        // Add action listeners
        view.getAddButton().addActionListener(new AddInventoryHandler());
        view.getInventoryTable().addMouseListener(new InventoryTableClickHandler());
        view.getBackButton().addActionListener(new RedirectToDashboard());
        
        // Load initial data
        loadInventoryToTable();
    }

    class RedirectToDashboard implements ActionListener {
           @Override
        public void actionPerformed(ActionEvent e) {
            WardenDashboardView dash = new WardenDashboardView();
            UserDao userDao = new UserDao();
            UserData user = userDao.getUserById(currentUserId);
            new WardenDashboardController(dash, user).open();
            view.dispose();
        }
    }

    private class InventoryTableClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = view.getInventoryTable();
            int row = table.getSelectedRow();

            if (row != -1 && e.getClickCount() == 1) {
                int itemId = Integer.parseInt(table.getValueAt(row, 0).toString());
                String itemName = table.getValueAt(row, 1).toString();
                int initialCount = Integer.parseInt(table.getValueAt(row, 2).toString());
                int ongoingCount = Integer.parseInt(table.getValueAt(row, 3).toString());
                String itemStatus = table.getValueAt(row, 4).toString();

                showPopup(itemId, itemName, initialCount,ongoingCount, itemStatus);
            }
        }
    }

    private void showPopup(int itemId, String itemName,int initialCount, int ongoingCount, String itemStatus) {
        JFrame popup = new JFrame("Inventory Actions");
        popup.setSize(300, 200);
        popup.setLayout(new GridLayout(2, 2));
        popup.setLocationRelativeTo(view);

        JButton readButton = new JButton("Read Info");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        popup.add(readButton);
        popup.add(updateButton);
        popup.add(deleteButton);
        popup.add(cancelButton);
        popup.setVisible(true);

readButton.addActionListener(e -> {
    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.add(new JLabel("Item ID:"));
    panel.add(new JLabel(String.valueOf(itemId)));
    panel.add(new JLabel("Item Name:"));
    panel.add(new JLabel(itemName));
    panel.add(new JLabel("Initial Count:"));
    panel.add(new JLabel(String.valueOf(initialCount))); // Added this
    panel.add(new JLabel("Ongoing Count:"));
    panel.add(new JLabel(String.valueOf(ongoingCount)));
    panel.add(new JLabel("Status:"));
    panel.add(new JLabel(itemStatus));

    JOptionPane.showMessageDialog(popup, panel, "Item Info", JOptionPane.PLAIN_MESSAGE);
});


updateButton.addActionListener(e -> {
    JPanel panel = new JPanel(new GridLayout(0, 1));

    JTextField initialField = new JTextField(); // Add initial count field
    JTextField ongoingField = new JTextField(String.valueOf(ongoingCount));
    JTextField statusField = new JTextField(itemStatus);
    JTextField costField = new JTextField(); // optional; default empty

    panel.add(new JLabel("Enter new initial count:"));
    panel.add(initialField);

    panel.add(new JLabel("Enter new ongoing count:"));
    panel.add(ongoingField);

    panel.add(new JLabel("Enter new status:"));
    panel.add(statusField);

    panel.add(new JLabel("Enter new cost per item:"));
    panel.add(costField);

    int result = JOptionPane.showConfirmDialog(popup, panel, "Update Item", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            int newInitialCount = Integer.parseInt(initialField.getText().trim());
            int newOngoingCount = Integer.parseInt(ongoingField.getText().trim());
            String newStatus = statusField.getText().trim();
            double newCost = Double.parseDouble(costField.getText().trim());

            dao.updateInventory(new InventoryData(itemId, itemName, newInitialCount, newOngoingCount, newStatus, newCost, currentUserId));
            popup.dispose();
            loadInventoryToTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(popup, "Invalid number input. Please check initial, ongoing count, and cost.");
        }
    }
});



        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(popup, "Are you sure you want to delete " + itemName + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteInventory(itemId);
                popup.dispose();
                loadInventoryToTable();
            }
        });

        cancelButton.addActionListener(e -> popup.dispose());
    }

    // Update loadInventoryToTable method
    private void loadInventoryToTable() {
        List<InventoryData> inventoryList = dao.getInventoryByUser(currentUserId);
        DefaultTableModel model = (DefaultTableModel) view.getInventoryTable().getModel();
        model.setRowCount(0); // Clear existing rows

        for (InventoryData item : inventoryList) {
            model.addRow(new Object[]{
                item.getItemId(),
                item.getItemName(),
                item.getInitialCount(),
                item.getOngoingCount(),
                item.getItemStatus()
            });
        }
    }

    // Update AddInventoryHandler
    class AddInventoryHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get values directly using view methods
                InventoryData item = new InventoryData(
                    view.getItemNameField(),
                    view.getInitialCountField(),
                    view.getOngoingCountField(),
                    view.getSelectedStatus(),
                    view.getCostPerItemField(),
                    currentUserId
                );

                if (dao.addInventory(item)) {
                    JOptionPane.showMessageDialog(view, "Item added successfully");
                    loadInventoryToTable();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add item");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, 
                    "Please enter valid numbers for counts and cost",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void open() {
        view.setVisible(true);
    }
    public void close() {
        view.dispose();
    }
}
