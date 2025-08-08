package raisetech.NewStudent.Management.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うRest　APIとして実行されるcontrollerです。
 */
@RestController
public class StudentController {

  @Autowired
  private StudentService service;

  @Autowired
  private StudentRepository studentRepository;



  public StudentController(StudentService service) {
    this.service = service;

  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行わない。
   *
   * @return　受講生一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   * @param id　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(Integer.parseInt(id));
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
    studentDetail.setStudentCourseList(Arrays.asList(new StudentCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }



  @GetMapping("/updateStudent/{id}")
  public String showEditForm(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    if (studentDetail == null) {
      return "redirect:/studentList"; // または 404エラーページへ
    }
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  /**
   * 受講生の登録を行います。
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail>registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生登録の更新を行います。
   * キャンセルフラグの更新もここで行います（論理削除）
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }


}
