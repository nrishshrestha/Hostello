/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostello.model;

/**
 *
 * @author ACER
 */


public class StudentData {
    private int student_id;
    private String name;
    private String email;
    private String phone;
    private String sex;
    private int age;
    private String occupation;
    private String institution;
    private String profile_picture;
    private int room_id;
    private int user_id;
    private String roomNo;
    // Empty constructor
    public StudentData() {}

    // Constructor without ID (for inserts)
    public StudentData(String name, String email, String phone, String sex, int age,
                       String occupation, String institution, String profile_picture,
                       int room_id, int user_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.age = age;
        this.occupation = occupation;
        this.institution = institution;
        this.profile_picture = profile_picture;
        this.room_id = room_id;
        this.user_id = user_id;
    }

    // Full constructor with ID (for updates)
    public StudentData(int student_id, String name, String email, String phone, String sex, int age,
                       String occupation, String institution, String profile_picture,
                       int room_id, int user_id) {
        this.student_id = student_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.age = age;
        this.occupation = occupation;
        this.institution = institution;
        this.profile_picture = profile_picture;
        this.room_id = room_id;
        this.user_id = user_id;
    }

    public String getRoomNo() {
    return roomNo;
}

public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
}
    
    
    // Getters and setters
    public int getStudentId() {
        return student_id;
    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public void setProfilePicture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public int getRoomId() {
        return room_id;
    }

    public void setRoomId(int room_id) {
        this.room_id = room_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
}
