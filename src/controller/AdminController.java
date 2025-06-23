/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ACER
 */
import dao.UserDao;
import model.UserData;
import view.AdminView;
import view.AdminDashboardView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminController {
    private AdminView view;
    private UserDao userDao;
private int currentUserId;


    public AdminController(AdminView view, int userId) {
        this.view = view;
        this.userDao = new UserDao();
         this.currentUserId = userId;
        this.view.getAddButton().addActionListener(new AdminController.AddAdminHandler());
        this.view.getAdminTable().addMouseListener(new AdminController.AdminTableClickHandler());
     
        this.view.getBackButton().addActionListener(new AdminController.RedirectToAdminDashboard());


        loadAdminTable();
    }

    
    class RedirectToAdminDashboard implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AdminDashboardView dash = new AdminDashboardView();
        UserDao userDao = new UserDao();
        UserData user = userDao.getUserById(currentUserId);
        new AdminDashboardController(dash,user).open();
        view.dispose();
    }
}

    
    
    
    
    
    
    private void loadAdminTable() {
        List<UserData> admins = userDao.getAllAdmins();
        String[] columnNames = {"ID", "Name", "Email"};
        String[][] data = new String[admins.size()][3];

        for (int i = 0; i < admins.size(); i++) {
            UserData a = admins.get(i);
            data[i][0] = String.valueOf(a.getUserId());
            data[i][1] = a.getUsername();
            data[i][2] = a.getEmail();
        }

        view.getAdminTable().setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private class AddAdminHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameField();
            String email = view.getEmailField();
            String password = view.getPasswordField();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields are required.");
                return;
            }

            UserData user = new UserData(name, email, password, "admin");
            boolean success = userDao.addAdmin(user);

            if (success) {
                JOptionPane.showMessageDialog(view, "Admin added successfully.");
                loadAdminTable();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add admin.");
            }
        }
    }

    private class AdminTableClickHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            JTable table = view.getAdminTable();
            int row = table.getSelectedRow();
            if (row == -1 || e.getClickCount() != 1) return;

            int userId = Integer.parseInt(table.getValueAt(row, 0).toString());
            String name = table.getValueAt(row, 1).toString();
            String email = table.getValueAt(row, 2).toString();

            showPopup(userId, name, email);
        }
    }

    private void showPopup(int id, String name, String email) {
        JFrame popup = new JFrame("Admin Actions");
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
            JOptionPane.showMessageDialog(popup, readPanel, "Admin Info", JOptionPane.PLAIN_MESSAGE);
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
                UserData updated = new UserData(id, nameField.getText(), emailField.getText(), passField.getText(), "admin");
                if (userDao.updateUser(updated)) {
                    JOptionPane.showMessageDialog(popup, "Updated successfully.");
                    popup.dispose();
                    loadAdminTable();
                } else {
                    JOptionPane.showMessageDialog(popup, "Failed to update.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(popup, "Are you sure you want to delete this admin?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (userDao.deleteUser(id)) {
                    JOptionPane.showMessageDialog(popup, "Deleted successfully.");
                    popup.dispose();
                    loadAdminTable();
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
