package raisetech.NewStudent.Management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private StudentRepository repository;

	private Map<String, String> studentMap = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// ■ 名前から学生情報を取得（GET）
	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		if (student == null) {
			return "該当する学生が見つかりません";
		}
		return student.getName() + " " + student.getAge() + "歳";
	}

	// ■ 学生を登録（POST）
	@PostMapping("/student")
	public String registerStudent(@RequestParam String name, @RequestParam int age) {
		repository.registerStudent(name, age);
		return "登録しました: " + name + "（" + age + "歳）";
	}

	// ■ Mapへの追加（練習用）
	@PatchMapping("/student")
	public void updateStudentName(String name, int age) {
		repository.updateStudent(name, age);
	}

	@DeleteMapping("/student")
	public  void deleteStudent(String name){

	}


	// ■ Map一覧表示（練習用）
	@GetMapping("/students")
	public Map<String, String> getStudents() {
		return studentMap;
	}
}
