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

  // 全受講生を取得（必要であれば）
  @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled AS canceled FROM students")
  List<Student> findAllStudents();


  // ✅ キャンセルされていない受講生を取得（演習課題対応）
  @Select("SELECT * FROM students WHERE is_canceled = false")
  List<Student> findActiveStudents();

  // ✅ 30代の受講生を取得（キャンセル除外）
  @Select("SELECT * FROM students WHERE age >= 30 AND age < 40 AND is_canceled = false")
  List<Student> findStudentsIn30s();

  // ✅ Javaコースの受講情報を取得（キャンセル条件は必要に応じて追加）
  @Select("SELECT * FROM students_courses WHERE course_name LIKE '%Java%'")
  List<StudentCourses> findJavaCourses();

  @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled AS canceled FROM students WHERE id = #{id}")
  Student findStudentById(int id);



  // ▼ 新規受講生を登録
  @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, isDeleted, is_canceled) " +
      "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, #{isDeleted}, #{canceled})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  // ▼ 受講生の更新処理（キャンセルフラグも含む）
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email}, area = #{area}, " +
      "age = #{age}, sex = #{sex}, remark = #{remark}, isDeleted = #{isDeleted}, is_canceled = #{canceled} WHERE id = #{id}")
  void updateStudent(Student student);

  // ▼ 受講生を削除（論理削除または物理削除）
  @Delete("DELETE FROM students WHERE id = #{id}")
  void deleteStudent(int id);



}
