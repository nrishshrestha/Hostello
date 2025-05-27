/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.AdminDashboardView;
import view.LoginView;

/**
 *
 * @author ACER
 */
public class AdminDashboardController {
    private AdminDashboardView view;
    
        public AdminDashboardController() {
        this.view = new AdminDashboardView();
        this.view.getLogoutButton().addActionListener(e -> close());
    }

    public void open() {
        view.setVisible(true);
    }

    private void close() {
        view.dispose();
       
        new LoginController(new LoginView()).open();
       
    }

}
