package raisetech.NewStudent.Management.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;

@Mapper
public interface StudentRepository {

  // 全受講生を取得
  @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled AS canceled FROM students")
  List<Student> search();

  // キャンセルされていない受講生を取得
  @Select("SELECT * FROM students WHERE is_canceled = false")
  List<Student> findActiveStudents();

  // 30代の受講生を取得
  @Select("SELECT * FROM students WHERE age >= 30 AND age < 40 AND is_canceled = false")
  List<Student> findStudentsIn30s();

  // IDから1人の受講生を取得（旧：searchStudent として使いたい場合）
  @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled AS canceled FROM students WHERE id = #{id}")
  Student searchStudent(int id);  // ← 名前そのまま維持！

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   * @param studentId　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourses> searchStudentCourse(int studentId);  // ← 名前そのまま維持！

  // すべての受講生コースを取得（検索一覧で使用）
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentCoursesList();

  // Javaコースのみを抽出
  @Select("SELECT * FROM students_courses WHERE course_name LIKE '%Java%'")
  List<StudentCourses> findJavaCourses();

  /**
   * 受講生を新規登録します。
   * IDに関しては自動採番を行う。
   * @param student　受講生
   */
  @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled) " +
      "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。　IDに関しては自動採番を行う。
   * @param studentCourse　受講生コース情報
   */
  @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at) " +
      "VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourses studentCourse);

  /**
   * 受講生を更新します。
   * @param student　受講生
   */
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email}, area = #{area}, " +
      "age = #{age}, sex = #{sex}, remark = #{remark}, isDeleted = #{isDeleted}, is_canceled = #{canceled} WHERE id = #{id}")
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   * @param studentCourse　受講生コース情報
   */
  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourses studentCourse);


  // 受講生を削除
  @Delete("DELETE FROM students WHERE id = #{id}")
  void deleteStudent(int id);
}
