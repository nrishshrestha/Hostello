/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.LoginView;
import view.WardenDashboardView;
import model.UserData;
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
        
        
        String name=user.getUsername();
        
        this.WardenDashboardView.getWelcomeLabel().setText("Welcome"+" " +name);
    }

         
         
         
         
    public void open() {
        this.WardenDashboardView.setVisible(true);
    }

    private void close() {
        this.WardenDashboardView.dispose();
       new LoginController(new LoginView()).open();
    }
}
