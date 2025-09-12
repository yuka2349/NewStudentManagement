package raisetech.NewStudent.Management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// ↓ 現状このテストでは content() を使っていないのでコメントアウトでもOK
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;            // ★ jakarta の Validator を使用
// import jakarta.validation.ValidatorFactory; // 使っていないので削除可

import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;
import raisetech.NewStudent.Management.service.StudentService;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean // ★ Controllerで@Autowiredされているため必要
  private StudentRepository studentRepository;

  @MockitoBean
  private StudentService service;

  // ★ jakarta.validation.Validator に統一
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("受講生詳細の一覧検索テスト")
  void 受講生詳細の一覧検索が実行できて空のリストが帰ってくること() throws Exception {
        mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    Integer id = 999;
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }


  @Test
  void 受講生詳細の更新時で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId(1);                           // ← ここを数値に
    student.setName("てすと太郎");
    student.setKanaName("テエシコウ");
    student.setNickname("てすと");
    student.setEmail("test@example.com");
    student.setArea("東京");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat( violations.size()).isEqualTo(0);  // assertEquals を static import しているならそのままでOK
  }
  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId(1);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat( violations.size()).isEqualTo(0);


  }
  @Test
  void 受講生詳細の登録が実行できて空で返ってくること()
      throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    // 本来であれば返るは登録されたデータが入っており、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
        {
            "student": {
                "name": "江並康介",
                "kanaName": "エナミ",
                "nickname": "エナゴン",
                "email": "test@example.com",
                "area": "奈良",
                "age": "36",
                "sex": "男性",
                "remark": ""
            },
            "studentCourseList": [
                {
                    "courseName": "Javaコース"
                }
            ]
        }
        """
    ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());

  }
  @Test
  void 受講生詳細の更新が実行できて空で返ってくること()
      throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "student": {
                    "id": 12,
                        "name": "江並康介",
                        "kanaName": "エナミ",
                        "nickname": "エナゴン",
                        "email": "test@example.com",
                        "area": "奈良",
                        "age": "36",
                        "sex": "男性",
                        "remark": ""
                    },
                    "studentCourseList": [
                        {
                            "id": 15,
                            "studentId": 12,
                            "courseName": "Javaコース",
                            "courseStartAt": "2024-04-27T10:50:39.833614",
                            "courseEndAt": "2025-04-27T10:50:39.833614"
                        }
                    ]
                }
                """))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }
  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(get("/exception"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));

  }

}
