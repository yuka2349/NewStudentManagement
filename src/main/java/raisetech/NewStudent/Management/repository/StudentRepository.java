package raisetech.NewStudent.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;

/**
 * 受講生情報を扱うリポジトリ
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです
 */


@Mapper
public interface StudentRepository {

  /**
   * 　全件検索します。
   *
   * @return　全件検索した受講生情報脳一覧
   */
  // students　全件取り出す
  @Select("SELECT * FROM students")
  List<Student> search();

  // students_courses　全件取り出す
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentsCourses();

  /**
   * 年齢が30代の人のみを抽出
   */
  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> findStudentsIn30s();

  /**
   * Javaコースだけ取り出し
   *
   * @return
   */
  @Select("SELECT * FROM students_courses WHERE course_name = 'Javaコース'")
  List<StudentCourses> findJavaCourses();

  @Insert("INSERT INTO students(name,kana_name,nickname,email,area,age,remark,isDeleted) VALUES(#{name},#{kanaName},#{nickname},#{email},#{area},#{age},#{sex},#{remark},false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudent (Student student);
  @Insert("INSERT INTO students_courses(student_id,course_name,course_start_at,course_end_at,status)"
      + "VALUES(${studentId},#{courseName},#{courseStartAt},#{courseEndAt},#{status})")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudentCourses(StudentCourses studentCourses);
}