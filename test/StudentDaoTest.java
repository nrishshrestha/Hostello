import dao.StudentDao;
import model.StudentData;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.List;

public class StudentDaoTest {
    private StudentDao studentDao;
    private StudentData testStudent;

    @Before
    public void setUp() {
        studentDao = new StudentDao();
        testStudent = new StudentData();
        testStudent.setName("Test Student");
        testStudent.setEmail("student@test.com");
        testStudent.setPhone("1234567890");
        testStudent.setSex("M");
        testStudent.setAge(20);
        testStudent.setOccupation("Student");
        testStudent.setInstitution("Test University");
        testStudent.setProfilePicture("");
        testStudent.setRoomId(1);
        testStudent.setUserId(1);
        studentDao.addStudent(testStudent);
    }

    @After
    public void tearDown() {
        List<StudentData> students = studentDao.getStudentsByWarden(1);
        for (StudentData s : students) {
            if ("student@test.com".equals(s.getEmail())) {
                studentDao.deleteStudent(s.getStudentId());
            }
        }
    }

    @Test
    public void testAddStudent() {
        StudentData s = new StudentData();
        s.setName("Another Student");
        s.setEmail("another@student.com");
        s.setPhone("0987654321");
        s.setSex("F");
        s.setAge(21);
        s.setOccupation("Student");
        s.setInstitution("Test University");
        s.setProfilePicture("");
        s.setRoomId(1);
        s.setUserId(1);
        assertTrue(studentDao.addStudent(s));
    }

    @Test
    public void testGetStudentsByWarden() {
        List<StudentData> students = studentDao.getStudentsByWarden(1);
        assertFalse(students.isEmpty());
    }

    @Test
    public void testUpdateStudent() {
        List<StudentData> students = studentDao.getStudentsByWarden(1);
        StudentData s = students.get(0);
        s.setName("Updated Name");
        assertTrue(studentDao.updateStudent(s));
    }

    @Test
    public void testDeleteStudent() {
        StudentData s = new StudentData();
        s.setName("Delete Me");
        s.setEmail("deleteme@student.com");
        s.setPhone("0000000000");
        s.setSex("M");
        s.setAge(22);
        s.setOccupation("Student");
        s.setInstitution("Test University");
        s.setProfilePicture("");
        s.setRoomId(1);
        s.setUserId(1);
        studentDao.addStudent(s);
        List<StudentData> students = studentDao.getStudentsByWarden(1);
        int idToDelete = -1;
        for (StudentData st : students) {
            if ("deleteme@student.com".equals(st.getEmail())) {
                idToDelete = st.getStudentId();
            }
        }
        assertTrue(studentDao.deleteStudent(idToDelete));
    }
}