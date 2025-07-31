package raisetech.NewStudent.Management.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;


@Controller
public class StudentController {

  @Autowired
  private StudentService service;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private StudentConverter converter;

  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCourseList();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
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

  // ★ 新規登録フォーム表示
  @GetMapping("/registerStudent")
  public String showRegisterStudentForm(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourses(Arrays.asList(new StudentCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  // ★ 新規登録処理
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.registerStudents(studentDetail);
    System.out.println(studentDetail.getStudent().getName() + "さんが新規受講生として登録されました。");
    return "redirect:/studentList";
  }

  @GetMapping("/updateStudent/{id}")
  public String showEditForm(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.findStudentDetailById(id);
    if (studentDetail == null) {
      return "redirect:/studentList"; // または 404エラーページへ
    }
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }


  // ★ 更新処理
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }
}
