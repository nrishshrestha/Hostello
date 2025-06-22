/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.LoginView;
import view.WardenDashboardView;
import model.UserData;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import controller.RoomController;
import dao.UserDao;
import hostello.view.RoomView;
import javax.swing.JOptionPane;
import view.InventoryView;
import view.StudentView;

/**
 *
 * @author ACER
 */
public class WardenDashboardController {
     private WardenDashboardView WardenDashboardView;
     private UserData user;
     private LoginController exsistingUser=new LoginController(new LoginView());
     
    
     public WardenDashboardController(WardenDashboardView WardenDashboardView,UserData user) {
        this.WardenDashboardView=WardenDashboardView;
        
        this.WardenDashboardView.getLogoutButton().addActionListener(e -> close());
        this.WardenDashboardView.getRoomButton().addActionListener(new WardenDashboardController.RedirectToRoom());
        this.WardenDashboardView.getStudentButton().addActionListener(new WardenDashboardController.RedirectToStudent());
        this.WardenDashboardView.getInventoryButton().addActionListener(new WardenDashboardController.RedirectToInventory());
        this.WardenDashboardView.getDeleteAccountButton().addActionListener(e -> deleteAccount());
        String name=user.getUsername();
        this.user=user;
        this.WardenDashboardView.getWelcomeLabel().setText("Welcome"+" " +name);
    
     }
     
 
         
         
         
    public void open() {
        this.WardenDashboardView.setVisible(true);
    }

    private void close() {
        this.WardenDashboardView.dispose();
       new LoginController(new LoginView()).open();
    }
    
    
    class RedirectToRoom implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WardenDashboardView.dispose();
        new RoomController(new RoomView(), user.getUserId()).open();



        }
        
    }
    
     class RedirectToStudent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WardenDashboardView.dispose();
        new StudentController(new StudentView(), user.getUserId()).open();

        }
        
    }
    
    
    class RedirectToInventory implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WardenDashboardView.dispose();
        new InventoryController(new InventoryView(), user.getUserId()).open();

        }
        
    }
    
    private void deleteAccount() {
    int confirm = JOptionPane.showConfirmDialog(
        WardenDashboardView,
        "Are you sure you want to permanently delete your account?",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        UserDao userDao = new UserDao();
        boolean deleted = userDao.deleteUser(user.getUserId());

        if (deleted) {
            JOptionPane.showMessageDialog(WardenDashboardView, "Account deleted successfully.");
            WardenDashboardView.dispose();
            new LoginController(new LoginView()).open(); // Go back to login
        } else {
            JOptionPane.showMessageDialog(WardenDashboardView, "Failed to delete account.");
        }
    }
}

    
}
