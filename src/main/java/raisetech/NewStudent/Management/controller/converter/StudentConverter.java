package raisetech.NewStudent.Management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourses> convertStudentCourses = studentCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
