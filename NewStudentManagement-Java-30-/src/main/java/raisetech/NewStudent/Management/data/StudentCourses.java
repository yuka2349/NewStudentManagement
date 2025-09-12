package raisetech.NewStudent.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description ="受講生コース")
@Getter
@Setter

  public class StudentCourses {
    private int id;
    private String studentId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private String status;
  }


