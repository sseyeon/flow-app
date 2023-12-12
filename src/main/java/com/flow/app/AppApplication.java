package com.flow.app;

import com.flow.app.model.Extension;
import com.flow.app.repository.ExtensionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AppApplication {

	private final ExtensionRepository extensionRepository;

	public AppApplication(ExtensionRepository extensionRepository) {
		this.extensionRepository = extensionRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	@PostConstruct
	public void init() {
		// 초기 데이터 추가
		List<String> extensions = Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js");

		for (String extension : extensions) {
			Extension extensionEntity = new Extension(extension, LocalDateTime.now(), true, "INACTIVE");
			extensionRepository.save(extensionEntity);
		}
	}
}
