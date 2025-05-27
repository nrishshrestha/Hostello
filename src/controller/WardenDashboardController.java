/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.LoginView;
import view.WardenDashboardView;

/**
 *
 * @author ACER
 */
public class WardenDashboardController {
     private WardenDashboardView view;
     public WardenDashboardController() {
        this.view = new WardenDashboardView();
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
