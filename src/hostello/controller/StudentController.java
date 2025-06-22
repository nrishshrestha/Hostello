/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.controller;

/**
 *
 * @author ACER
 */


import hostello.dao.RoomDao;
import hostello.dao.StudentDao;
import hostello.dao.UserDao;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import hostello.model.StudentData;
import hostello.model.UserData;
import hostello.view.StudentView;
import hostello.view.WardenDashboardView;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import hostello.model.RoomData;

public class StudentController {

    private StudentView view;
    private int currentUserId;
    private String uploadedProfilePath = null;

    public StudentController(StudentView view, int userId) {
        this.view = view;
        this.currentUserId = userId;
        
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

        // Respond only on single-click
        if (row != -1 && e.getClickCount() == 1) {
            int studentId = Integer.parseInt(table.getValueAt(row, 0).toString());

            // Fetch full student record from DB
            StudentDao studentDao = new StudentDao();
            StudentData student = studentDao.getStudentById(studentId); // Must return all fields including profile

            if (student != null) {
                showStudentPopup(student); // Call the updated version
            } else {
                JOptionPane.showMessageDialog(view, "Failed to load student details.");
            }
        }
    }
}

 
 
 
 private void showStudentPopup(StudentData student) {
    JFrame popup = new JFrame("Student Actions");
    popup.setSize(400, 250);
    popup.setLayout(new GridLayout(2, 2));
    popup.setLocationRelativeTo(view);

    JButton readButton = new JButton("Read Info");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton cancelButton = new JButton("Cancel");

    StudentDao studentDao = new StudentDao();

    // 🧠 1. READ button
    readButton.addActionListener(e -> {
    JPanel readPanel = new JPanel();
    readPanel.setLayout(new BorderLayout(10, 10)); // Top = Profile, Center = Info

    // 🖼️ Profile Picture on Top
    JPanel profilePanel = new JPanel();
    profilePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    if (student.getProfilePicture() != null) {
        try {
            ImageIcon icon = new ImageIcon(student.getProfilePicture());
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            profilePanel.add(picLabel);
        } catch (Exception ex) {
            profilePanel.add(new JLabel("Failed to load image."));
        }
    } else {
        profilePanel.add(new JLabel("No image uploaded."));
    }

    // 📋 Two-column Info Layout
    JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2-column grid

    infoPanel.add(new JLabel("Name:"));
    infoPanel.add(new JLabel(student.getName()));
    infoPanel.add(new JLabel("Email:"));
    infoPanel.add(new JLabel(student.getEmail()));
    infoPanel.add(new JLabel("Phone:"));
    infoPanel.add(new JLabel(student.getPhone()));
    infoPanel.add(new JLabel("Sex:"));
    infoPanel.add(new JLabel(student.getSex()));
    infoPanel.add(new JLabel("Age:"));
    infoPanel.add(new JLabel(String.valueOf(student.getAge())));
    infoPanel.add(new JLabel("Occupation:"));
    infoPanel.add(new JLabel(student.getOccupation()));
    infoPanel.add(new JLabel("Institution:"));
    infoPanel.add(new JLabel(student.getInstitution()));
    infoPanel.add(new JLabel("Room No:"));
    infoPanel.add(new JLabel(student.getRoomNo()));

    // Assemble panels
    readPanel.add(profilePanel, BorderLayout.NORTH);
    readPanel.add(infoPanel, BorderLayout.CENTER);

    // 🧱 Create a compact square dialog
    JOptionPane.showMessageDialog(popup, readPanel, "Student Info", JOptionPane.PLAIN_MESSAGE);
});

    // 🛠️ 2. UPDATE button
updateButton.addActionListener(e2 -> {
    // Main panel with horizontal box layout
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding

    // Left panel for profile pic
    JPanel picPanel = new JPanel();
    picPanel.setLayout(new BoxLayout(picPanel, BoxLayout.Y_AXIS));
    picPanel.setPreferredSize(new Dimension(120, 150));
    picPanel.setMaximumSize(new Dimension(120, 150));

    JLabel profileLabel = new JLabel("Profile Picture", SwingConstants.CENTER);
    profileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel picDisplay = new JLabel();
    picDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);

    if (student.getProfilePicture() != null) {
        ImageIcon icon = new ImageIcon(student.getProfilePicture());
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        picDisplay.setIcon(new ImageIcon(scaledImage));
    } else {
        picDisplay.setText("No Image");
        picDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    }

    JButton uploadButton = new JButton("Change Picture");
    uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    final String[] newProfilePath = {student.getProfilePicture()};

    uploadButton.addActionListener(ev -> {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(view);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            newProfilePath[0] = selectedFile.getAbsolutePath();
            ImageIcon newIcon = new ImageIcon(newProfilePath[0]);
            Image scaled = newIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            picDisplay.setIcon(new ImageIcon(scaled));
            picDisplay.setText(null);
        }
    });

    picPanel.add(profileLabel);
    picPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    picPanel.add(picDisplay);
    picPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    picPanel.add(uploadButton);
    
    
picPanel.setPreferredSize(new Dimension(120, 220));
picPanel.setMaximumSize(new Dimension(120, 220));

uploadButton.setMaximumSize(new Dimension(120, 30));

    // Right panel for fields - grid 2 columns
    JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // rows auto, 2 cols, 10px gaps

    JTextField nameField = new JTextField(student.getName());
    JTextField emailField = new JTextField(student.getEmail());
    JTextField phoneField = new JTextField(student.getPhone());
    JTextField sexField = new JTextField(student.getSex());
    JTextField ageField = new JTextField(String.valueOf(student.getAge()));
    JTextField occupationField = new JTextField(student.getOccupation());
    JTextField institutionField = new JTextField(student.getInstitution());
    JTextField roomField = new JTextField(student.getRoomNo());

    // Add label-field pairs, two per row naturally
    fieldsPanel.add(new JLabel("Name:"));
    fieldsPanel.add(nameField);

    fieldsPanel.add(new JLabel("Email:"));
    fieldsPanel.add(emailField);

    fieldsPanel.add(new JLabel("Phone:"));
    fieldsPanel.add(phoneField);

    fieldsPanel.add(new JLabel("Sex:"));
    fieldsPanel.add(sexField);

    fieldsPanel.add(new JLabel("Age:"));
    fieldsPanel.add(ageField);

    fieldsPanel.add(new JLabel("Occupation:"));
    fieldsPanel.add(occupationField);

    fieldsPanel.add(new JLabel("Institution:"));
    fieldsPanel.add(institutionField);

    fieldsPanel.add(new JLabel("Room No:"));
    fieldsPanel.add(roomField);

    // Combine panels
    mainPanel.add(picPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(20, 0))); // space between pic and fields
    mainPanel.add(fieldsPanel);

    int result = JOptionPane.showConfirmDialog(popup, mainPanel, "Update Student", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            int age = Integer.parseInt(ageField.getText());

            RoomDao roomDao = new RoomDao();
            int roomId = roomDao.getRoomIdByRoomNo(roomField.getText());
            if (roomId == -1) {
                JOptionPane.showMessageDialog(view, "Invalid room number.");
                return;
            }

            student.setName(nameField.getText());
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());
            student.setSex(sexField.getText());
            student.setAge(age);
            student.setOccupation(occupationField.getText());
            student.setInstitution(institutionField.getText());
            student.setRoomId(roomId);
            student.setUserId(currentUserId);
            student.setProfilePicture(newProfilePath[0]);

            boolean success = studentDao.updateStudent(student);
            if (success) {
                JOptionPane.showMessageDialog(view, "Student updated.");
                loadStudentsToTable();
                popup.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Update failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Age must be a number.");
        }
    }
});


    // 🗑️ 3. DELETE button
    deleteButton.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(popup, "Delete student " + student.getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = studentDao.deleteStudent(student.getStudentId());
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

    // 👇 Add buttons to popup
    popup.add(readButton);
    popup.add(updateButton);
    popup.add(deleteButton);
    popup.add(cancelButton);
    popup.setVisible(true);
}

    
    
    
private void loadStudentsToTable() {
    StudentDao studentDao = new StudentDao();
    List<StudentData> studentList = studentDao.getStudentsByWarden(currentUserId);

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
    List<RoomData> rooms = roomDao.getAvailableRoomsByWarden(currentUserId);

    JComboBox<String> roomDropdown = view.getRoomDropdown();
    Map<String, Integer> roomMap = view.getRoomMap(); // Make sure this exists in View

    roomDropdown.removeAllItems();
    roomMap.clear();

    for (RoomData room : rooms) {
        String label = room.getRoomNo() + " - " + room.getRoomStatus(); // e.g. "101 - Available"
        roomDropdown.addItem(label);
        roomMap.put(label, room.getRoomId()); // Store the mapping
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
            String selectedRoom = view.getSelectedRoom();
            int roomId = view.getRoomMap().getOrDefault(selectedRoom, -1);
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
                    institution, uploadedProfilePath, roomId, currentUserId
                );

                StudentDao studentDao = new StudentDao();
                boolean success = studentDao.addStudent(student);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Student added successfully.");
                    uploadedProfilePath = null; 
                     loadAvailableRooms();
                     loadStudentsToTable();
// Reset profile path
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
            UserData user = userDao.getUserById(currentUserId);
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
