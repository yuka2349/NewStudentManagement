package raisetech.NewStudent.Management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

@Test
  void 受講生の全件検索を行えること(){
  List<Student> actual = sut.search();
  assertThat(actual.size()).isEqualTo(5);
}

@Test
  void 受講生の登録が行えること(){
  Student student = new Student();
  student.setName("江並公史");
  student.setKanaName("エナミコウジ");
  student.setNickname("エナミ");
  student.setEmail("test@example.com");
  student.setArea("奈良県");
  student.setAge(36);
  student.setSex("男性");
  student.setRemark("");
  student.setDeleted(false);

  sut.registerStudent(student);
  List<Student> actual = sut.search();
  assertThat(actual.size()).isEqualTo(6);

}

  @Test
  @DisplayName("受講生コースの登録が行えること")
  void 受講生コースの登録が行えること() {
    StudentCourses sc = new StudentCourses();
    sc.setStudentId(1);
    sc.setCourseName("Spring基礎");
    sc.setCourseStartAt(LocalDateTime.of(2024, 4, 1, 10, 0));
    sc.setCourseEndAt(LocalDateTime.of(2024, 6, 1, 18, 0));

    sut.registerStudentCourse(sc);

    List<StudentCourses> all = sut.searchStudentCoursesList();
    assertThat(all).hasSize(9); // 8 + 1
    assertThat(all).extracting(StudentCourses::getCourseName).contains("Spring基礎");
  }


  @Test
  @DisplayName("ID指定で受講生を1件取得できること")
  void ID指定で受講生を1件取得できること() {
    Student s = sut.searchStudent(1);
    assertThat(s).isNotNull();
    assertThat(s.getId()).isEqualTo(1);
    // 固定値を使うなら初期データに合わせて：
    // assertThat(s.getName()).isEqualTo("山田太郎");
  }

  @Test
  @DisplayName("受講生IDに紐づくコースを取得できること")
  void 受講生IDに紐づくコースを取得できること() {
    List<StudentCourses> courses = sut.searchStudentCourse(1);
    assertThat(courses).isNotEmpty();
    // 初期データ例では id=1 に 3件
    assertThat(courses).hasSize(3);
    assertThat(courses).extracting(StudentCourses::getCourseName)
        .contains("Javaコース", "Web制作コース");
  }

  @Test
  @DisplayName("すべての受講生コースを取得できること")
  void すべての受講生コースを取得できるこ() {
    List<StudentCourses> all = sut.searchStudentCoursesList();
    assertThat(all).hasSize(8); // ← 初期データ数に合わせて調整
  }

  @Test
  @DisplayName("受講生を更新できること")
  void 受講生を更新できること() {
    Student before = sut.searchStudent(1);
    before.setArea("北海道");
    before.setRemark("メモ更新");
    before.setDeleted(true); // "isDeleted" 列に対応（Mapper 側で対応済み前提）

    sut.updateStudent(before);

    Student after = sut.searchStudent(1);
    assertThat(after.getArea()).isEqualTo("北海道");
    assertThat(after.getRemark()).isEqualTo("メモ更新");
    assertThat(after.isDeleted()).isTrue();
  }

  @Test
  @DisplayName("受講生コースのコース名を更新できること")
  void 受講生コースのコース名を更新できること() {
    // 学籍1のコース一覧から、最初の1件を更新対象にする
    List<StudentCourses> beforeList = sut.searchStudentCourse(1);
    StudentCourses target = beforeList.get(0);
    int targetId = target.getId();              // ← 更新対象の行IDを控える
    String oldName = target.getCourseName();

    // 1行だけ名称変更
    target.setCourseName("改名後コース");
    sut.updateStudentCourse(target);

    // 学籍1の一覧を再取得し、更新した「その行」だけ確認
    List<StudentCourses> afterList = sut.searchStudentCourse(1);
    StudentCourses updated = afterList.stream()
        .filter(c -> c.getId() == targetId)     // ← IDで1行を特定
        .findFirst()
        .orElseThrow();

    // その行の名前が変わっていること
    assertThat(updated.getCourseName()).isEqualTo("改名後コース");
    assertThat(updated.getCourseName()).isNotEqualTo(oldName);

    // （任意）件数は変わっていないこと＝1行更新であること
    assertThat(afterList).hasSize(beforeList.size());
  }


  @Test
  @DisplayName("受講生を削除できること")
  void 受講生を削除できること() {
    // 前提：id=1 を削除
    sut.deleteStudent(1);

    List<Student> all = sut.search();
    assertThat(all).hasSize(4);
    // 物理削除なら null、論理削除なら isDeleted=true になる想定に応じて変更
    Student s = sut.searchStudent(1);
    assertThat(s).isNull(); // ← 物理削除の想定。論理削除なら適宜変更
  }
}
