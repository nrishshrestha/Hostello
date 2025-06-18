/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.LoginController;
import model.UserData;
import view.AdminDashboardView;
import view.LoginView;

/**
 *
 * @author ACER
 */
public class AdminDashboardController {
    private AdminDashboardView AdminDashboardView;
    
        public AdminDashboardController(AdminDashboardView AdminDashboardView,UserData user) {
        
        this.AdminDashboardView.getLogoutButton().addActionListener(e -> close());
        
        String name=user.getUsername();
        
        this.AdminDashboardView.getwelcomeLabel().setText("Welcome"+" " +name);
    }

    public void open() {
        this.AdminDashboardView.setVisible(true);
    }

    private void close() {
        this.AdminDashboardView.dispose();
       
        new LoginController(new LoginView()).open();
       
    }

}
