package raisetech.NewStudent.Management;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

  public class StudentCourses {
    private int id;
    private String studentId;
    private String courseName;
    private String courseStartAt;
    private String courseEndAt;
    private String status;
  }


