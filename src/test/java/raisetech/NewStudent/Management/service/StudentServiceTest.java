package raisetech.NewStudent.Management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }


@Test
  void 受講生一覧検索_リポジトリとコンパーターの処理が適切に呼び出せていること(){
  //MOCK化をする
  //事前準備
  //StudentService sut = new StudentService(repository,converter);
  List<Student> studentList = new ArrayList<>();
  List<StudentCourses> studentCoursesList = new ArrayList<>();
  when(repository.search()).thenReturn(studentList);
  when(repository.searchStudentCoursesList()).thenReturn(studentCoursesList);
  List<StudentDetail> actual = sut.searchStudentList();
  //実行

  //検証
  verify(repository,Mockito.times(1)).search();
  verify(repository,Mockito.times(1)).searchStudentCoursesList();
  verify(converter,Mockito.times(1)).convertStudentDetails(studentList,studentCoursesList);
  //後処理
//ここでDB
}
  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    Integer id = 999;
    Student student = new Student();
    student.setId(id);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

    StudentDetail actual = sut.searchStudent(id);

    // Nullチェックを追加
    assertNotNull(actual, "StudentDetail が null になっています");

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);

    assertEquals(id, actual.getStudent().getId());
  }

  @Test
  void 受講生登録_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student();                 // ダミーの Student
    StudentCourses studentCourses = new StudentCourses(); // ダミーの StudentCourses
    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourses));

    // 実行
    sut.registerStudent(studentDetail);

    // 検証: repository のメソッドが正しく呼ばれたか確認
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourses);
  }
  @Test
  void 受講生更新_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student();                 // ダミーの Student
    StudentCourses studentCourses = new StudentCourses(); // ダミーの StudentCourses
    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourses));

    // 実行
    sut.updateStudent(studentDetail);

    // 検証: repository のメソッドが正しく呼ばれたか確認
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourses); // ★ repository にある実際のメソッド名に合わせて修正
  }

  @Test
  void 受講者の登録_初期化処理がされること() {
    // arrange
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourses studentCourse = new StudentCourses();

    // act
    sut.initStudentsCourse(studentCourse, student);

    // assert
    Assertions.assertEquals("999", studentCourse.getStudentId());
    Assertions.assertEquals(LocalDateTime.now().getHour(),
        studentCourse.getCourseStartAt().getHour());
    Assertions.assertEquals(LocalDateTime.now().plusYears(1).getYear(),
        studentCourse.getCourseEndAt().getYear());
  }



}