package raisetech.NewStudent.Management.service;

import org.springframework.stereotype.Service;
import raisetech.NewStudent.Management.exception.TestException;


  @Service
  public class ApplicationService {
    public void validateStudent(String studentName) throws TestException {
      if (studentName == null || studentName.isBlank()) {
        throw new TestException("学生名が不正です。");
      }
    }
  }

