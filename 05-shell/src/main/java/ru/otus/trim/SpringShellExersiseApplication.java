package ru.otus.trim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringShellExersiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShellExersiseApplication.class, args);
	}

//	public ApplicationEventMulticaster applicationEventMulticaster (){
//		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		return eventMulticaster;
//	}

}
