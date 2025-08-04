package raisetech.NewStudent.Management.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;

@Getter
@Setter
public class StudentDetail {
  private Student student;
  private List<StudentCourses> studentCourses;


}
