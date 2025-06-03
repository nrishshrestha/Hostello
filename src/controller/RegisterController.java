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
import view.RegisterView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.*;


public class RegisterController {
    private RegisterView registerView;
    private UserDao userDao;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.userDao = new UserDao();  // DAO to talk to database

        // Add listeners
        this.registerView.getRegisterButton().addActionListener(new RegisterAction());
        this.registerView.getRedirectLoginLabel().addMouseListener(new RedirectToLogin());
    }

    public void open() {
        registerView.setVisible(true);
    }

    public void close() {
        registerView.dispose();
    }

    // Inner class for Register button
    class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = registerView.getNameInput();
            String email = registerView.getEmailInput();
            String password = registerView.getPasswordInput();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerView, "All fields are required.");
                return;
            }

            UserData userRegister = new UserData(name, email, password,"warden");

            boolean success = userDao.register(userRegister);
            
            
            if (success) {
                JOptionPane.showMessageDialog(registerView, "Registration successful!");
                close();
                LoginController lcontroller = new LoginController(new LoginView());
                 lcontroller.open();
 // Open Login page
            } else {
                JOptionPane.showMessageDialog(registerView, "Registration failed. Try again.");
            }
        }
    }

    // Inner class for "Already have account?" link
    class RedirectToLogin implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            close();
            new LoginController(new LoginView()).open();
        }
        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
}
