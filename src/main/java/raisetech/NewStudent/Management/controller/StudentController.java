package raisetech.NewStudent.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  private StudentRepository studentRepository;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

  @GetMapping("/students/30s")
  public List<Student> getStudentsIn30s() {
    return studentRepository.findStudentsIn30s();
  }

  @GetMapping("/courses/java")
  public List<StudentCourses> getJavaCourses() {
    return studentRepository.findJavaCourses();
  }
}
