/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello;
import hostello.controller.RegisterController;
import hostello.view.RegisterView;

/**
 *
 * @author ACER
 */
public class Hostello {
    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(() -> {
            RegisterView registerView = new RegisterView();
            RegisterController registerController = new RegisterController(registerView);
            registerController.open();  // Entry point
            System.out.println("Entry Point");
//        });
    }
}