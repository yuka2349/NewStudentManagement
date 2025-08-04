package raisetech.NewStudent.Management.data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private boolean isDeleted;
  private boolean canceled; // ← これだけでOK！（getter/setter自動生成される）
}
