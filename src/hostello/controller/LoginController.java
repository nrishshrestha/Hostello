/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.controller;

/**
 *
 * @author ACER
 */
import hostello.controller.WardenDashboardController;
import hostello.controller.mail.SMTPSMailSender;
import hostello.dao.UserDao;
import hostello.model.UserData;
import hostello.view.LoginView;
import hostello.view.AdminDashboardView;
import hostello.view.WardenDashboardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import hostello.model.ResetPasswordRequest;


public class LoginController {
    private LoginView loginView;
    private UserDao userDao;
    public UserData existingUser;
    public LoginController(LoginView loginView) {
        this.loginView =loginView;
        this.userDao = new UserDao();
ResetPassword resetPass=new ResetPassword();

        this.loginView.getLoginButton().addActionListener(new LoginHandler());
        this.loginView.getRedirectLogin().addMouseListener(new RedirectToRegister());
        this.loginView.getForgotPassword().addMouseListener(new ResetPassword());
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
    
    
    
    public UserData getExistingUser(){
  return existingUser;
  
  }
    
    
    class RedirectToRegister implements  MouseListener {
        
        
        @Override
        public void mouseClicked(MouseEvent e) {
             close();
        new hostello.controller.RegisterController(new hostello.view.RegisterView()).open();
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

    
    
    
    class ResetPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String email =JOptionPane.showInputDialog(loginView,"Enter your email");
            if(email.isEmpty()){
                JOptionPane.showMessageDialog(loginView,"Email cannot be empty");
            }else{
                UserDao userDao =new UserDao();
                boolean emailExists =userDao.checkEmail(email);
                if(!emailExists){
                    JOptionPane.showMessageDialog(loginView,"Email does not exist");
                }else{
                    String otp ="987586";
            
                    String title ="Reset Password Verification";
                    String body ="The otp to reset your password is"+otp;
                    boolean mailSent=SMTPSMailSender.sendMail(email,title,body);
                    if(!mailSent){
                        JOptionPane.showMessageDialog(loginView, "Failed to send OTP. Try again later.");
                    }else{
                        String otpReceived=JOptionPane.showInputDialog(loginView,"Enetr your otp code");
                        if(!otp.equals(otpReceived)){
                            JOptionPane.showMessageDialog(loginView,"Otp did not match");
                        }else{
                            String password=JOptionPane.showInputDialog(loginView,"Enter your new password");
                            if (password.trim().isEmpty()){
                                
                            JOptionPane.showMessageDialog(loginView,"password cannot be empty");
                            
                          
                            }else{
                                ResetPasswordRequest resetReq =new ResetPasswordRequest(email,password);
                                boolean updateResult=userDao.resetPassword(resetReq);
                                if(updateResult){
                                    JOptionPane.showMessageDialog(loginView,"password has been changed");
                                }
                            }
                        }
                    }
                }
            }
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
