package raisetech.NewStudent.Management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.NewStudent.Management.controller.converter.StudentConverter;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.domain.StudentDetail;
import raisetech.NewStudent.Management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  @Mock
  private StudentService sut;

  @BeforeEach
  void before(){
  sut = new StudentService(repository,converter);
  }


@Test
  void 受講生一覧検索_リポジトリとコンパーターの処理が適切に呼び出せていること(){
  //MOCK化をする
  //事前準備
  //StudentService sut = new StudentService(repository,converter);
  List<Student> studentList = new ArrayList<>();
  List<StudentCourses> studentCoursesList = new ArrayList<>();
  when(repository.search()).thenReturn(studentList);
  when(repository.searchStudentCoursesList()).thenReturn(studentCoursesList);
  List<StudentDetail> actual = sut.searchStudentList();
  //実行

  //検証
  verify(repository,Mockito.times(1)).search();
  verify(repository,Mockito.times(1)).searchStudentCoursesList();
  verify(converter,Mockito.times(1)).convertStudentDetails(studentList,studentCoursesList);
  //後処理
//ここでDB
}
}