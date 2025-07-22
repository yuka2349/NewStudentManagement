package raisetech.NewStudent.Management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

@Controller
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
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCourseList();
    model.addAttribute("studentList",converter.convertStudentDetails(students, studentCourses));

    return "studentList";
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

  @GetMapping("/newStudent")
  public String newStudent(Model model){
   model.addAttribute("studentDetail", new StudentDetail());
   return "registerStudent";
  }
  @PostMapping("/registerStudent")
public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "registerStudent";
    }
    //①新規受講生情報を登録する処理を実装する。
      service.registerStudents(studentDetail);
    //②コース情報も一緒に登録できるように実装する。コースは単体でよい。

    System.out.println(studentDetail.getStudent().getName()+"さんが新規受講生として登録されました。");
    return  "redirect:/studentList";
  }

}
