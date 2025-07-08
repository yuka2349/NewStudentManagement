package raisetech.NewStudent.Management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  // students　全件取り出す
  @Select("SELECT * FROM students")
  List<Student> search();

  // students_courses　全件取り出す
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> findAll();



}
