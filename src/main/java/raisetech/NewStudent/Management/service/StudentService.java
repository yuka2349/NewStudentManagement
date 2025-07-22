package raisetech.NewStudent.Management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;
@Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }
  public List<Student> searchStudentList() {
    return repository.search();
  }
  public List<StudentCourses> searchStudentCourseList() {
    return repository.searchStudentsCourses();
  }
@Transactional
  public void registerStudents(StudentDetail studentDetail){
  repository.registerStudent(studentDetail.getStudent());
  //TODO:コース情報登録
  }
}
