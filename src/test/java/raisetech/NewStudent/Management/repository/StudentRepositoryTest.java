package raisetech.NewStudent.Management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.NewStudent.Management.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

@Test
  void 受講生の全件検索を行えること(){
  List<Student> actual = sut.search();
  assertThat(actual.size()).isEqualTo(5);
}

@Test
  void 受講生の登録が行えること(){
  Student student = new Student();
  student.setName("江並公史");
  student.setKanaName("エナミコウジ");
  student.setNickname("エナミ");
  student.setEmail("test@example.com");
  student.setArea("奈良県");
  student.setAge(36);
  student.setSex("男性");
  student.setRemark("");
  student.setDeleted(false);

  sut.registerStudent(student);
  List<Student> actual = sut.search();
  assertThat(actual.size()).isEqualTo(6);

}

}
