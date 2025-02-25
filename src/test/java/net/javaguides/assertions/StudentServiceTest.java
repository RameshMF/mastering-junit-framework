package net.javaguides.assertions;

import net.javaguides.Student;
import net.javaguides.StudentNotFoundException;
import net.javaguides.StudentService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    public void getStudentsTest(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh");

        List<Student> listOfStudents = studentService.getStudents();
        studentService.addStudent(student);

        boolean actualResult = listOfStudents.isEmpty();

        // assertTrue(actualResult);

        // assertTrue(() -> actualResult);

        // assertTrue(actualResult, "List of students is empty!");

        // assertTrue(() -> actualResult, "List of students is empty!");

        // assertTrue(actualResult, () -> "List of students is empty!");

        assertTrue(() -> actualResult, () -> "List of students is empty!");
    }

    @Test
    public void getStudentsTestUsingAssertFalse(){

        StudentService studentService = new StudentService();

       // Student student = new Student(1, "Ramesh");
        List<Student> studentList = studentService.getStudents();
       // studentService.addStudent(student);
        boolean actualResult = studentList.isEmpty();

        // assertFalse(actualResult);

        // assertFalse(actualResult, "Student list should not be empty!");

        // assertFalse(() -> actualResult);

        // assertFalse(() -> actualResult, "Student list should not be empty!");

       // assertFalse(actualResult, () -> "Student list should not be empty!");

        assertFalse(() -> actualResult, () -> "Student list should not be empty!");

    }

    @Test
    public void getStudentByIdTestUsingAssertNull(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh");
        studentService.addStudent(student);

        Student actualObject = studentService.getStudentById(2);

       // assertNull(actualObject);

       // assertNull(actualObject, "Student object is not null!");

        assertNull(actualObject, () -> "Student object is not null!");
    }

    @Test
    public void getStudentByIdTestUsingAssertNotNull(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh");
        studentService.addStudent(student);

        Student actualObject = studentService.getStudentById(1);

        // assertNotNull(actualObject);

        // assertNotNull(actualObject, "Student is null!");

        assertNotNull(actualObject, () -> "Student is null!");

    }

    @Test
    public void getStudentByIdTestUsingAssertEquals(){
        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh");
        studentService.addStudent(student);

        Student actualObject = studentService.getStudentById(1);

        assertEquals(1, actualObject.getId());
        assertEquals("Ramesh", actualObject.getName());

        assertEquals(student, actualObject);

        assertEquals(1, actualObject.getId(), "Student Id is not equal");

        assertEquals("Ram", actualObject.getName(), () -> "Student name is not equal");

    }

    @Test
    public void getStudentByIdTestUsingAssertNotEquals(){
        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh");

        Student student1 = new Student(2, "Ram");

        studentService.addStudent(student);

        Student actualObject = studentService.getStudentById(1);

        assertNotEquals(2, actualObject.getId());

        assertNotEquals("Ram", actualObject.getName());

        assertNotEquals(2, actualObject.getId(), "Student Id is equal");

        assertNotEquals("Ram", actualObject.getName(), () -> "Student Id is equal");

        assertNotEquals(student1, actualObject);

    }

    @Test
    public void getStudentNamesByDepartmentTestUsingAssertArrayEquals(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh", "Science");
        Student student1 = new Student(2, "Umesh", "Science");
        Student student2 = new Student(3, "Ram", "Arts");
        studentService.addStudent(student);
        studentService.addStudent(student1);
        studentService.addStudent(student2);

        String[] actualArray = studentService.getStudentNamesByDepartment("Science");
        String[] expectedArray = {"Ramesh", "Umesh"};

       // assertArrayEquals(expectedArray, actualArray);

       // assertArrayEquals(expectedArray, actualArray, "Student names are not equal");

        // assertArrayEquals(expectedArray, actualArray, () -> "Student names are not equal");

        Integer[] actualStudentIds = studentService.getStudentIdsByDepartment("Science");
        Integer[] expectedStudentIds = {1, 2};

        //assertArrayEquals(expectedStudentIds, actualStudentIds);

       // assertArrayEquals(expectedStudentIds, actualStudentIds, "Student Ids are not equal");

        assertArrayEquals(expectedStudentIds, actualStudentIds, () -> "Student Ids are not equal");
    }

    @Test
    public void getStudentNameListByDepartmentTestUsingAssertIterableEquals(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh", "Science");
        Student student1 = new Student(2, "Umesh", "Science");
        Student student2 = new Student(3, "Ram", "Arts");
        studentService.addStudent(student);
        studentService.addStudent(student1);
        studentService.addStudent(student2);

        List<String> actualStudentNameList = studentService.getStudentNameListByDepartment("Science");
        List<String> expectedStudentNameList = Arrays.asList("Ramesh", "Umesh", "Ram");

       // assertIterableEquals(expectedStudentNameList, actualStudentNameList);

       // assertIterableEquals(expectedStudentNameList, actualStudentNameList, "Student name list is not equal");


        // assertIterableEquals(expectedStudentNameList, actualStudentNameList, () -> "Student name list is not equal");

        List<Integer> actualStudentIdList = Arrays.asList(1, 2);
        List<Integer> expectedStudentIdList = studentService.getStudentIdListByDepartment("Science");

       // assertIterableEquals(expectedStudentIdList, actualStudentIdList);

        //assertIterableEquals(expectedStudentIdList, actualStudentIdList, "Student id list is not equal");

        assertIterableEquals(expectedStudentIdList, actualStudentIdList, () -> "Student id list is not equal");

    }

    @Test
    public void getStudentByNameTestUsingAssertThrows(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh", "Science");
        studentService.addStudent(student);

//        assertThrows(StudentNotFoundException.class, () -> {
//            studentService.getStudentByName("Umesh");
//        });

//        assertThrows(StudentNotFoundException.class, () -> {
//            studentService.getStudentByName("Ramesh");
//        }, "StudentNotFoundException should be thrown. But, it wasn't");

        assertThrows(RuntimeException.class, () -> {
            studentService.getStudentByName("Umesh");
        }, () -> "StudentNotFoundException should be thrown. But, it wasn't");
    }

    @Test
    public void getStudentByNameTestUsingAssertThrowsExactly(){

        StudentService studentService = new StudentService();

        Student student = new Student(1, "Ramesh", "Science");
        studentService.addStudent(student);

//        assertThrowsExactly(StudentNotFoundException.class, () -> {
//            studentService.getStudentByName("Umesh");
//        });

//        assertThrowsExactly(NullPointerException.class, () -> {
//            studentService.getStudentByName("Umesh");
//        }, "StudentNotFoundException should be thrown. But, it wasn't");

//        assertThrowsExactly(StudentNotFoundException.class, () -> {
//            studentService.getStudentByName("Ramesh");
//        }, () -> "StudentNotFoundException should be thrown. But, it wasn't");

        StudentNotFoundException exception = assertThrowsExactly(StudentNotFoundException.class, () -> {
            studentService.getStudentByName("Umesh");
        }, () -> "StudentNotFoundException should be thrown. But, it wasn't");

        assertEquals("Student not found with name: Umesh", exception.getMessage());

    }

}