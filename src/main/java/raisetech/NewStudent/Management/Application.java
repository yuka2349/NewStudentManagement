package raisetech.NewStudent.Management;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {

	private String name="Enami Kouji";
	private String age ="37";
	// 名前と年齢を管理するMap
	private Map<String, String> studentMap = new HashMap<>();
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public  String getStudentInfo(){
		return name + " "+age +"歳";
	}

	@PostMapping("/studentInfo")
	public void setName(String name,String age){
		this.name = name;
		this.age = age;
	}
	@PostMapping("/studentName")
	public void upStudentName(String name){
		this.name = name;
	}
	// 学生を追加する（POST）
	@PostMapping("/student")
	public String addStudent(@RequestParam String name, @RequestParam String age) {
		studentMap.put(name, age);
		return "追加しました: " + name + "（" + age + "歳）";
	}

	// 全ての学生情報を取得する（GET）
	@GetMapping("/students")
	public Map<String, String> getStudents() {
		return studentMap;
	}
}
