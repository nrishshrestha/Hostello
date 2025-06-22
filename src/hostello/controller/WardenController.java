/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.controller;

/**
 *
 * @author ACER
 */


import hostello.dao.UserDao;
import hostello.model.UserData;
import hostello.view.WardenView;
import hostello.view.WardenDashboardView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import hostello.view.AdminDashboardView;

public class WardenController {
    private WardenView view;
    private UserDao userDao;
    private int currentUserId;
    public WardenController(WardenView view,int userId) {
      this.view = view;
      this.userDao = new UserDao();
      this.currentUserId = userId;
        this.view.getAddButton().addActionListener(new AddWardenHandler());
        this.view.getWardenTable().addMouseListener(new WardenTableClickHandler());
        this.view.getBackButton().addActionListener(e -> {
          this.view.getBackButton().addActionListener(new RedirectToAdminDashboard());

        });

        loadWardenTable();
    }

    class RedirectToAdminDashboard implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          AdminDashboardView dash = new AdminDashboardView();
          UserDao userDao = new UserDao();
          UserData user = userDao.getUserById(currentUserId);
          new AdminDashboardController(dash, user).open();
          view.dispose();
      }
  }



    private void loadWardenTable() {
        List<UserData> wardens = userDao.getAllWardens();
        String[] columnNames = {"ID", "Name", "Email"};
        String[][] data = new String[wardens.size()][3];

        for (int i = 0; i < wardens.size(); i++) {
            UserData w = wardens.get(i);
            data[i][0] = String.valueOf(w.getUserId());
            data[i][1] = w.getUsername();
            data[i][2] = w.getEmail();
        }

        view.getWardenTable().setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private class AddWardenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameField();
            String email = view.getEmailField();
            String password = view.getPasswordField();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields are required.");
                return;
            }

            UserData user = new UserData(name, email, password, "warden");
            boolean success = userDao.register(user);

            if (success) {
                JOptionPane.showMessageDialog(view, "Warden added successfully.");
                loadWardenTable();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add warden.");
            }
        }
    }

    private class WardenTableClickHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            JTable table = view.getWardenTable();
            int row = table.getSelectedRow();
            if (row == -1 || e.getClickCount() != 1) return;

            int userId = Integer.parseInt(table.getValueAt(row, 0).toString());
            String name = table.getValueAt(row, 1).toString();
            String email = table.getValueAt(row, 2).toString();

            showPopup(userId, name, email);
        }
    }

    private void showPopup(int id, String name, String email) {
        JFrame popup = new JFrame("Warden Actions");
        popup.setSize(300, 200);
        popup.setLayout(new GridLayout(2, 2));
        popup.setLocationRelativeTo(view.getMainFrame());

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
            JPanel readPanel = new JPanel(new GridLayout(0, 2));
            readPanel.add(new JLabel("ID:"));
            readPanel.add(new JLabel(String.valueOf(id)));
            readPanel.add(new JLabel("Name:"));
            readPanel.add(new JLabel(name));
            readPanel.add(new JLabel("Email:"));
            readPanel.add(new JLabel(email));
            JOptionPane.showMessageDialog(popup, readPanel, "Warden Info", JOptionPane.PLAIN_MESSAGE);
        });

        updateButton.addActionListener(e -> {
            JTextField nameField = new JTextField(name);
            JTextField emailField = new JTextField(email);
            JTextField passField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("New Name:"));
            panel.add(nameField);
            panel.add(new JLabel("New Email:"));
            panel.add(emailField);
            panel.add(new JLabel("New Password:"));
            panel.add(passField);

            int result = JOptionPane.showConfirmDialog(popup, panel, "Update Info", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                UserData updated = new UserData(id, nameField.getText(), emailField.getText(), passField.getText(), "warden");
                if (userDao.updateUser(updated)) {
                    JOptionPane.showMessageDialog(popup, "Updated successfully.");
                    popup.dispose();
                    loadWardenTable();
                } else {
                    JOptionPane.showMessageDialog(popup, "Failed to update.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(popup, "Are you sure you want to delete this warden?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (userDao.deleteUser(id)) {
                    JOptionPane.showMessageDialog(popup, "Deleted successfully.");
                    popup.dispose();
                    loadWardenTable();
                } else {
                    JOptionPane.showMessageDialog(popup, "Deletion failed.");
                }
            }
        });

        cancelButton.addActionListener(e -> popup.dispose());
    }

    public void open() {
        view.setVisible(true);
    }
    
    public void close() {
        view.dispose();
    }
    
    
}
