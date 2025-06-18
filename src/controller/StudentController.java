/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ACER
 */


import dao.RoomDao;
import dao.StudentDao;
import dao.UserDao;
import java.awt.GridLayout;
import model.StudentData;
import model.UserData;
import view.StudentView;
import view.WardenDashboardView;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class StudentController {

    private StudentView view;
    private int currentWardenId;
    private String uploadedProfilePath = null;

    public StudentController(StudentView view, int wardenId) {
        this.view = view;
        this.currentWardenId = wardenId;
        
        this.view.getAddButton().addActionListener(new AddStudentHandler());
        this.view.getProfileUploadButton().addActionListener(new UploadProfileHandler());
        this.view.getBackButton().addActionListener(new RedirectToDashboard());
        this.view.getStudentTable().addMouseListener(new StudentTableClickHandler());

        // Load available rooms on start
        loadAvailableRooms();
loadStudentsToTable();
        // Set listeners
        
    }
    
    private class StudentTableClickHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = view.getStudentTable();
        int row = table.getSelectedRow();

        if (row != -1 && e.getClickCount() == 1) {
            int studentId = Integer.parseInt(table.getValueAt(row, 0).toString());
            String name = table.getValueAt(row, 1).toString();
            String roomNo = table.getValueAt(row, 2).toString();

            showStudentPopup(studentId, name, roomNo);
        }
    }
}

 private void showStudentPopup(int studentId, String name, String roomNo) {
    JFrame popup = new JFrame("Student Actions");
    popup.setSize(300, 200);
    popup.setLayout(new GridLayout(3, 1));
    popup.setLocationRelativeTo(view);

    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton cancelButton = new JButton("Cancel");

    updateButton.addActionListener(e -> {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField nameField = new JTextField(name);
        JTextField roomField = new JTextField(roomNo); // You can convert this to dropdown later

        panel.add(new JLabel("Update Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Update Room No:"));
        panel.add(roomField);

        int result = JOptionPane.showConfirmDialog(
            popup, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String updatedName = nameField.getText();
            String updatedRoom = roomField.getText();

            RoomDao roomDao = new RoomDao();
            int roomId = roomDao.getRoomIdByRoomNo(updatedRoomNo);

            if (roomId == -1) {
              JOptionPane.showMessageDialog(view, "Room No not found in database.");
              return;
          }

          
            StudentData updatedStudent = new StudentData();
            updatedStudent.setStudentId(studentId);
            updatedStudent.setName(updatedName);
            updatedStudent.setRoomNo(updatedRoom);

            boolean success = studentDao.updateStudent(updatedStudent);

            if (success) {
                JOptionPane.showMessageDialog(view, "Student updated.");
                loadStudentsToTable();
                popup.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Update failed.");
            }
        }
    });

    deleteButton.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(popup, "Delete student " + name + "?");

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = studentDao.deleteStudentById(studentId);

            if (success) {
                JOptionPane.showMessageDialog(view, "Student deleted.");
                loadStudentsToTable();
                popup.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Delete failed.");
            }
        }
    });

    cancelButton.addActionListener(e -> popup.dispose());

    popup.add(updateButton);
    popup.add(deleteButton);
    popup.add(cancelButton);
    popup.setVisible(true);
}
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
private void loadStudentsToTable() {
    StudentDao studentDao = new StudentDao();
    List<StudentData> studentList = studentDao.getStudentsByWarden(currentWardenId);

    String[] columnNames = {"Student ID", "Name", "Room No"};
    String[][] data = new String[studentList.size()][3];

    for (int i = 0; i < studentList.size(); i++) {
        StudentData s = studentList.get(i);
        data[i][0] = String.valueOf(s.getStudentId());
        data[i][1] = s.getName();
        data[i][2] = s.getRoomNo();
    }

    view.getStudentTable().setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
}

   
    private void loadAvailableRooms() {
        RoomDao roomDao = new RoomDao();
        List<String> roomNumbers = roomDao.getAvailableRoomNumbersByWarden(currentWardenId);

        JComboBox<String> roomDropdown = view.getRoomDropdown();
        roomDropdown.removeAllItems();
        for (String room : roomNumbers) {
            roomDropdown.addItem(room);
        }
    }

    
    
    
    
    
    /////////////////////////// ADD STUDENT //////////////////////////////////
    private class AddStudentHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Extract fields
            String name = view.getNameField();
            String email = view.getEmailField();
            String phone = view.getPhoneField();
            String sex = view.getSelectedSex();
            String ageStr = view.getAgeField();
            String occupation = view.getOccupationField();
            String institution = view.getInstitutionField();
            int selectedRoom = view.getSelectedRoom();

            // Validation
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || ageStr.isEmpty() || uploadedProfilePath == null) {
                JOptionPane.showMessageDialog(view, "All fields including profile picture must be filled.");
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);

                // Create student data object
                StudentData student = new StudentData(
                    name, email, phone, sex, age, occupation,
                    institution, uploadedProfilePath, selectedRoom, currentWardenId
                );

                StudentDao studentDao = new StudentDao();
                boolean success = studentDao.addStudent(student);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Student added successfully.");
                    uploadedProfilePath = null; // Reset profile path
                    // TODO: Refresh student table if implemented
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add student.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Age must be a number.");
            }
        }
    }

    //////////////////////////// UPLOAD PROFILE ////////////////////////////
    private class UploadProfileHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(view);

            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                uploadedProfilePath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(view, "Profile picture selected: " + uploadedProfilePath);
            }
        }
    }

    /////////////////////////// BACK BUTTON ///////////////////////////
    private class RedirectToDashboard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WardenDashboardView dash = new WardenDashboardView();
            UserDao userDao = new UserDao();
            UserData user = userDao.getUserById(currentWardenId);
            new WardenDashboardController(dash, user).open();
            view.dispose();
        }
    }

    /////////////////////// SHOW STUDENT VIEW ///////////////////////
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
}
