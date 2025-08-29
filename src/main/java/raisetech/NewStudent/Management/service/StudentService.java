package raisetech.NewStudent.Management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  // 編集画面で使用する受講生詳細情報の取得
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourses> studentCourse = repository.searchStudentCourse(student.getId());

    return converter.convertStudentDetail(student, studentCourse);
  }

  /**
   * 全受講生の一覧を取得（Student + Courses を統合したDTOで返却）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourses> studentCourseList = repository.searchStudentCoursesList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  // 受講コースの一覧取得（Javaのみ）
  public List<StudentCourses> searchStudentCourseList() {

    return repository.findJavaCourses();
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コースW情報を個別に党則氏、受講生コース情報に荷は受講生情報を紐づける値や日付情報（コース開始日など）を設定します。
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    if (studentDetail.getStudentCourseList() != null) {
      studentDetail.getStudentCourseList().forEach(studentsCourse -> {
        initStudentsCourse(studentsCourse, student);
        repository.registerStudentCourse(studentsCourse);
      });
    }

    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentsCourse
   * @param student
   */


  void initStudentsCourse(StudentCourses studentsCourse, Student student) {
    studentsCourse.setStudentId(String.valueOf(student.getId()));
    LocalDateTime now = LocalDateTime.now();
    studentsCourse.setCourseStartAt(now);
    studentsCourse.setCourseEndAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生の情報と受講生コース情報をそれぞれ更新します。
   * @param studentDetail　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
