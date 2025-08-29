package raisetech.NewStudent.Management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.exception.TestException;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うRest APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {

  @Autowired
  private StudentService service;           // final なし

  @Autowired
  private StudentRepository studentRepository; // final なし

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行わない。
   *
   * @return 受講生一覧（全件）
   */
  @Operation(summary ="",description="受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    //throws TestException {
    // 今はこのAPIを使わせない要件なので、明示的に例外を投げる（最小実装）
    //throw new TestException("現在のこのAPIは利用できません。URLは「students」を利用してください");
    // もし将来有効化するなら下記に切り替え：
     return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   * @param id 受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotNull Integer id) {
    return service.searchStudent(id);
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

  // ★ 新規登録フォーム表示（テンプレートを使う場合は @Controller 推奨。簡潔化のためそのまま残します）
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
      return "redirect:/studentList"; // または 404 エラーページへ
    }
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  /**
   * 受講生の登録を行います。
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary ="受講生登録",description="受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生登録の更新を行います。
   * キャンセルフラグの更新もここで行います（論理削除）
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  // --- ここで TestException を最小限にハンドリング ---
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
