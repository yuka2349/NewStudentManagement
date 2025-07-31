package raisetech.NewStudent.Management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;

@Service
public class StudentService {

  @Autowired
  private StudentRepository repository;

  // 編集画面で使用する受講生詳細情報の取得
  public StudentDetail findStudentDetailById(int id) {
    Student student = repository.findStudentById(id);
    if (student == null) {
      return null;
    }

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    // 今は全Javaコースを取得（本来は該当受講生のコースを取得するのが望ましい）
    List<StudentCourses> allCourses = repository.findJavaCourses();
    detail.setStudentCourses(allCourses);

    return detail;
  }

  // 一覧画面で使用する受講生リスト取得
  public List<Student> searchStudentList() {
    return repository.findAllStudents();
  }

  // 受講コースの一覧取得
  public List<StudentCourses> searchStudentCourseList() {
    return repository.findJavaCourses();
  }

  // 新規受講生の登録処理
  public void registerStudents(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
  }

  // 既存受講生の更新処理
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
  }
}
