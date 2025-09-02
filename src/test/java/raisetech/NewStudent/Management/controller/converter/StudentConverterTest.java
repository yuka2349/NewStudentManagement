package raisetech.NewStudent.Management.controller.converter;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること(){

    Student student = createStudent();

    StudentCourses studentCourses = new StudentCourses();
    studentCourses.setId(1);
    studentCourses.setStudentId("1");
    studentCourses.setCourseName("Javaコース");
    studentCourses.setCourseStartAt(LocalDateTime.now());
    studentCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourses> studentCourseList = List.of(studentCourses);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);

  }



  @Test
  void 受講生のリストと受講生コース情報のリストを渡した時に紐づかない受講生コース情報は除外されること(){
    Student student = createStudent();
    // 学生（ID=10）


    StudentCourses studentCourses = new StudentCourses();
    studentCourses.setId(1);
    studentCourses.setStudentId("2");
    studentCourses.setCourseName("Javaコース");
    studentCourses.setCourseStartAt(LocalDateTime.now());
    studentCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourses> studentCourseList = List.of(studentCourses);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();

  }
  private static Student createStudent() {
    Student student = new Student();
    student.setId(1);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setAge(36);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }
}