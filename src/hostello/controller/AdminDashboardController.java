/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.controller;

import hostello.controller.LoginController;
import hostello.dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import hostello.model.UserData;
import hostello.view.AdminDashboardView;
import hostello.view.AdminView;
import hostello.view.InventoryView;
import hostello.view.LoginView;
import hostello.view.StudentView;
import hostello.view.WardenDashboardView;
import hostello.view.WardenView;

/**
 *
 * @author ACER
 */
public class AdminDashboardController {
     private AdminDashboardView AdminDashboardView;
     private UserData user;
     private LoginController exsistingUser=new LoginController(new LoginView());
    
  
    
        public AdminDashboardController(AdminDashboardView AdminDashboardView,UserData user) 
        {
            this.AdminDashboardView=AdminDashboardView;
        this.AdminDashboardView.getLogoutButton().addActionListener(e -> close());
       this.AdminDashboardView.getWardenButton().addActionListener(new AdminDashboardController.RedirectToWarden());
        this.AdminDashboardView.getAdminButton().addActionListener(new AdminDashboardController.RedirectToAdmin());
        this.AdminDashboardView.getDeleteAccountButton().addActionListener(e -> deleteAccount());
        String name=user.getUsername();
        this.user=user;
        this.AdminDashboardView.getWelcomeLabel().setText("Welcome"+" " +name);
    
        }

    public void open() {
        this.AdminDashboardView.setVisible(true);
    }

    private void close() {
        this.AdminDashboardView.dispose();
        new LoginController(new LoginView()).open();
       
    }
    
    
    
    class RedirectToWarden implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AdminDashboardView.dispose();
        new WardenController(new WardenView(), user.getUserId()).open();

        }
        
    }
    
    
    class RedirectToAdmin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AdminDashboardView.dispose();
        new AdminController(new AdminView(), user.getUserId()).open();

        }
        
    }
    
    private void deleteAccount() {
    int confirm = JOptionPane.showConfirmDialog(
        AdminDashboardView,
        "Are you sure you want to permanently delete your account?",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        UserDao userDao = new UserDao();
        boolean deleted = userDao.deleteUser(user.getUserId());

        if (deleted) {
            JOptionPane.showMessageDialog(AdminDashboardView, "Account deleted successfully.");
            AdminDashboardView.dispose();
            new LoginController(new LoginView()).open(); // Go back to login
        } else {
            JOptionPane.showMessageDialog(AdminDashboardView, "Failed to delete account.");
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
