package demo;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private FsShell shell;

	@Override
	public void run(String... args) {
		setUp(new String[]{
				"/tmp",
				"/user/hive/input"
		});
		//===============================================================================
		for (FileStatus s : shell.lsr("/tmp")) {
			System.out.println("> " + s.getPath());
		}
		//===============================================================================
		if(!shell.test("/user/hive/input/passwd")) {
			shell.copyFromLocal("C:\\Users\\Bill_Wang\\workspace-demo\\spring-hadoop-samples\\hive\\data\\passwd", "/user/hive/input");
		}
		for (FileStatus s : shell.lsr("/user/hive/input/")) {
			System.out.println("> " + s.getPath());
		}

		//===============================================================================
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void setUp(String[] paths){
		for(String path : paths) {
			if (shell.test(path)) {
				break;
			} else {
				shell.mkdir(path);
			}
		}
	}

}
