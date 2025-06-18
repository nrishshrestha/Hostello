/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.RoomDao;
import dao.UserDao;
import java.awt.GridLayout;
import model.RoomData;
import view.roomView;
import view.WardenDashboardView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

import java.util.List;
import model.UserData;
/**
 *
 * @author ACER
 */

 public class RoomController {

  private roomView roomView;
  private RoomDao roomDao;
  private RoomData selectedRoom;
  private int currentUserId;

  public RoomController(roomView roomView, int userId) {
      this.roomView = roomView;
      this.roomDao = new RoomDao();
      this.currentUserId = userId;

      roomView.getAddButton().addActionListener(new RoomController.AddRoomHandler());
      roomView.getRoomTable().addMouseListener(new RoomTableClickHandler());
      roomView.getBackButton().addActionListener(new RedirectToDashboard());
      loadRoomsToTable();
  }

class RedirectToDashboard implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          WardenDashboardView wardenDashboardView= new WardenDashboardView();
          UserDao userDao=new UserDao();
          UserData user=userDao.getUserById(currentUserId);
          new WardenDashboardController(wardenDashboardView,user).open();
      }}

/////////////////////////////////////////////////////////////////////////////
  private class RoomTableClickHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = roomView.getRoomTable();
        int row = table.getSelectedRow();

        if (row != -1 && e.getClickCount() == 1) {
            int roomId = Integer.parseInt(table.getValueAt(row, 0).toString());
            String roomNo = table.getValueAt(row, 1).toString(); 
            String type = table.getValueAt(row, 2).toString();
            String status = table.getValueAt(row, 3).toString();
            double cost = Double.parseDouble(table.getValueAt(row, 4).toString());

            showPopup(roomId,roomNo, type, status, cost);
        }
    }
}
 /////////////////////////////////////////////////////////////////////////////////////////////

private void showPopup(int roomId, String roomNo, String type, String status, double cost) {
  JFrame popup = new JFrame("Room Actions");
  popup.setSize(300, 200);
  popup.setLayout(new GridLayout(3, 1));
  popup.setLocationRelativeTo(roomView.getMainFrame());

  JButton updateButton = new JButton("Update");
  JButton deleteButton = new JButton("Delete");
  JButton cancelButton = new JButton("Cancel");




updateButton.addActionListener(e -> {
    JPanel panel = new JPanel(new GridLayout(0, 1));

    JTextField typeField = new JTextField(type);
    JTextField statusField = new JTextField(status);
    JTextField costField = new JTextField(String.valueOf(cost));

    panel.add(new JLabel("Enter new room type:"));
    panel.add(typeField);

    panel.add(new JLabel("Enter new room status:"));
    panel.add(statusField);

    panel.add(new JLabel("Enter new cost:"));
    panel.add(costField);

    int result = JOptionPane.showConfirmDialog(
        popup, panel, "Update Room Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
        String newType = typeField.getText();
        String newStatus = statusField.getText();
        String newCostStr = costField.getText();

        try {
            double newCost = Double.parseDouble(newCostStr);
            System.out.println("Updating: " + roomNo + ", " + newType + ", " + newStatus + ", " + newCost);


            roomDao.updateRoom(new RoomData(roomId, roomNo, newType, newCost, newStatus, currentUserId));
            
            popup.dispose();
            loadRoomsToTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(popup, "Invalid cost input. Must be a number.");
        }
    }
});










  deleteButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(popup, "Are you sure you want to delete Room " + roomNo + "?");
      if (confirm == JOptionPane.YES_OPTION) {
          roomDao.deleteRoom(roomNo);
          
          popup.dispose();
          loadRoomsToTable();
      }
  });

  cancelButton.addActionListener(e -> popup.dispose());

  popup.add(updateButton);
  popup.add(deleteButton);
  popup.add(cancelButton);
  popup.setVisible(true);
}

////////////////////////////////////////////////////////////////////
public void open() {
  roomView.setVisible(true);
  // You can add more initialization here if needed
}
public void close() {
  roomView.setVisible(false);
  roomView.dispose();
  roomView.setVisible(true);  // Frees resources, closes the window properly
}



  private void loadRoomsToTable() {
    List<RoomData> roomList = roomDao.getRoomsByWarden(currentUserId);
   
    // Convert List<Room> to JTable data
    String[] columnNames = {"RoomId","Room No", "Type", "Status", "Cost"};
    String[][] data = new String[roomList.size()][5];

    for (int i = 0; i < roomList.size(); i++) {
        RoomData r = roomList.get(i); 
        data[i][0] = String.valueOf(r.getRoomId());
        data[i][1] = String.valueOf(r.getRoomNo());
        data[i][2] = r.getRoomType();
        data[i][3] = r.getRoomStatus();
        data[i][4] = String.valueOf(r.getRoomCost());
    }

    roomView.getRoomTable().setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    
  }



    

  class AddRoomHandler implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        String roomNo = roomView.getRoomNoField();
        double cost = roomView.getCostField();
        String type = roomView.getSelectedRoomType();
        String status = roomView.getSelectedRoomStatus();
        RoomData room = new RoomData(roomNo, type, cost ,status ,currentUserId);

        boolean success = roomDao.addRoom(room);
        
        if (success) {
          JOptionPane.showMessageDialog(roomView, "Room added successfully.");
          loadRoomsToTable();
      } else {
          JOptionPane.showMessageDialog(roomView, "Failed to add room.");
      }

      
      }}}

  

