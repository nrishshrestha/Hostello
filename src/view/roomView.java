/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.awt.Component;
import javax.swing.*;
/**
 *
 * @author ACER
 */
public class RoomView extends javax.swing.JFrame {
 
  private JFrame mainFrame;
    /**
     * Creates new form RoomView
     */
    public RoomView() {
        initComponents();
        // Hides the first column (Room_id)
roomTable.getColumnModel().getColumn(0).setMinWidth(0);
roomTable.getColumnModel().getColumn(0).setMaxWidth(0);
roomTable.getColumnModel().getColumn(0).setWidth(0);
roomTable.getColumnModel().getColumn(0).setResizable(false);
    }

public String getRoomNoField() {
        return roomNoField.getText();
    }

    public double  getCostField() {
        return Double.parseDouble(costField.getText());
    }

    public String getSelectedRoomType() {
      return (String) typeComboBox.getSelectedItem();
  }

 
public String getSelectedRoomStatus() {
  return (String) statusComboBox.getSelectedItem();
}

    public JButton getAddButton() {
        return addButton;
    }

  
    public JTable getRoomTable() {
        return roomTable;
    }
    public JButton getBackButton(){
      return backButton;
    }
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        statuslabel = new javax.swing.JLabel();
        roomNoField = new javax.swing.JTextField();
        RoomNoLabel1 = new javax.swing.JLabel();
        RoomTypeLabel = new javax.swing.JLabel();
        RoomNoLabel3 = new javax.swing.JLabel();
        costField = new javax.swing.JTextField();
        typeComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        backButton = new javax.swing.JButton();
        RoomBG = new javax.swing.JLabel();
        AllRoomsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addButton.setBackground(new java.awt.Color(204, 255, 204));
        addButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        addButton.setText("Add ");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        getContentPane().add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 90, -1));

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Room_id", "Room No", "Type", "Status", "Cost"
            }
        ));
        jScrollPane1.setViewportView(roomTable);
        if (roomTable.getColumnModel().getColumnCount() > 0) {
            roomTable.getColumnModel().getColumn(0).setMinWidth(0);
            roomTable.getColumnModel().getColumn(0).setPreferredWidth(0);
            roomTable.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 320, 120));

        statuslabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        statuslabel.setText("Status :");
        getContentPane().add(statuslabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 130, 40));

        roomNoField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        roomNoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomNoFieldActionPerformed(evt);
            }
        });
        getContentPane().add(roomNoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 100, 40));

        RoomNoLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        RoomNoLabel1.setText("Room No : ");
        getContentPane().add(RoomNoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 140, 30));

        RoomTypeLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        RoomTypeLabel.setText("Room Type :");
        getContentPane().add(RoomTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 150, 30));

        RoomNoLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        RoomNoLabel3.setText("Cost :");
        getContentPane().add(RoomNoLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 80, 30));

        costField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        costField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costFieldActionPerformed(evt);
            }
        });
        getContentPane().add(costField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 150, 40));

        typeComboBox.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1-Seater", "2-Seater", "3-Seater", "4-Seater" }));
        typeComboBox.setToolTipText("");
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(typeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 150, 40));

        statusComboBox.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "available", "occupied" }));
        getContentPane().add(statusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 150, 50));

        backButton.setText("⬅️");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        RoomBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagepicker/room.png"))); // NOI18N
        getContentPane().add(RoomBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, -1));

        AllRoomsLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        AllRoomsLabel.setText("All Rooms");
        getContentPane().add(AllRoomsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addButtonActionPerformed

    private void roomNoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomNoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomNoFieldActionPerformed

    private void costFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costFieldActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
<<<<<<< HEAD
        //</editor-fold>
=======
>>>>>>> fb580745053b605471ea10c3baa6d5814bc9708d

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AllRoomsLabel;
    private javax.swing.JLabel RoomBG;
    private javax.swing.JLabel RoomNoLabel1;
    private javax.swing.JLabel RoomNoLabel3;
    private javax.swing.JLabel RoomTypeLabel;
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JTextField costField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField roomNoField;
    private javax.swing.JTable roomTable;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JLabel statuslabel;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables

   public JFrame getMainFrame() {
       mainFrame = new JFrame("Room Management");
    return mainFrame;
}
}
