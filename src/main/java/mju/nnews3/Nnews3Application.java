package mju.nnews3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Nnews3Application {

	public static void main(String[] args) {
		SpringApplication.run(Nnews3Application.class, args);
	}

}
