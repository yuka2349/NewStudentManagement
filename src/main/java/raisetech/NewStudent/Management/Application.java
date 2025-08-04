package raisetech.NewStudent.Management;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import raisetech.NewStudent.Management.data.Student;
import raisetech.NewStudent.Management.data.StudentCourses;
import raisetech.NewStudent.Management.repository.StudentRepository;

@SpringBootApplication

public class Application {



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}




}
