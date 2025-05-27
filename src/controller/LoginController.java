/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ACER
 */
import controller.WardenDashboardController;
import dao.UserDao;
import model.UserData;
import view.LoginView;
import view.AdminDashboardView;
import view.WardenDashboardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class LoginController {
    private LoginView loginView;
    private UserDao userDao;
    public UserData existingUser;
    public LoginController(LoginView loginView) {
        this.loginView =loginView;
        this.userDao = new UserDao();

        this.loginView.getLoginButton().addActionListener(new LoginHandler());
        this.loginView.getRedirectLogin().addMouseListener(new RedirectToRegister());
//        this.loginView.getForgotPassword().addMouseListioner(listiener);
    }

   
public void open() {
        loginView.setVisible(true);
    }

    public void close() {
        loginView.dispose();
    }
    
  
    class LoginHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailTextField();
            String password = loginView.getPasswordField();
            WardenDashboardView wardenDashboardView= new WardenDashboardView();
            AdminDashboardView adminDashboardView = new AdminDashboardView();
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill in all fields");
                return;
            }

            existingUser = userDao.login(email, password);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(loginView, "Login Success");

              
                if (existingUser.getRole().equals("admin")) {
                    new AdminDashboardController(adminDashboardView, existingUser).open();
                } else {
                    new WardenDashboardController(wardenDashboardView,existingUser).open();
                }
                 close();
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid credentials");
            }
        }
    }
    
public UserData getExsistingUser(){
  return existingUser;
  
  }
    class RedirectToRegister extends  MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        close();
        new controller.RegisterController(new view.RegisterView()).open();
    }
    
    }

    
    
}
