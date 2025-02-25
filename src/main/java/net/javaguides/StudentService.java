package net.javaguides;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {

    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents(){
        return this.students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public Student getStudentById(int studentId){
        return students.stream()
                .filter((student) -> student.getId() == studentId)
                .findFirst()
                .orElse(null);

    }

    public String[] getStudentNamesByDepartment(String department){

        return students.stream()
                .filter((student) -> student.getDepartment().equals(department))
                .map(Student::getName)
                .toArray(String[]::new);

    }

    public Integer[] getStudentIdsByDepartment(String department){

        return students.stream()
                .filter((student) -> student.getDepartment().equals(department))
                .map(Student::getId)
                .toArray(Integer[]::new);

    }

    public List<String> getStudentNameListByDepartment(String department){

        return students.stream()
                .filter((student) -> student.getDepartment().equals(department))
                .map(Student::getName)
                .collect(Collectors.toList());

    }

    public List<Integer> getStudentIdListByDepartment(String department){

        return students.stream()
                .filter((student) -> student.getDepartment().equals(department))
                .map(Student::getId)
                .collect(Collectors.toList());

    }

    public Student getStudentByName(String name){

        return students.stream()
                .filter((student) -> student.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student not found with name: " + name));
    }
}
