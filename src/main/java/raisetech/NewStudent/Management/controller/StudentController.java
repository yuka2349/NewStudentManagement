package raisetech.NewStudent.Management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  private StudentRepository studentRepository;
  private StudentConverter studentConverter;

  public StudentController(StudentService service,StudentConverter converter) {
    this.service = service;
    this.converter=converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCourseList();

    return converter.convertStudentDetails(students, studentCourses);
  }



  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
//30代だけの抽出
  @GetMapping("/students/30s")
  public List<Student> getStudentsIn30s() {
    return studentRepository.findStudentsIn30s();
  }
//Javaコースだけの表示
  @GetMapping("/courses/java")
  public List<StudentCourses> getJavaCourses() {
    return studentRepository.findJavaCourses();
  }
}
