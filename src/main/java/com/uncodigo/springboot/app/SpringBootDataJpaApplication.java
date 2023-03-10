package com.uncodigo.springboot.app;

import com.uncodigo.springboot.app.helpers.ListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uncodigo.springboot.app.models.service.IUploadFileService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

	@Autowired
	IUploadFileService uploadFileService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ListenerContainer listenerContainer;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();

		String password = "123123";

		for (int i = 0; i < 2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}

		listenerContainer.postStartupPrint();

	}

}
