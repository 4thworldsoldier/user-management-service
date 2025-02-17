package com.babbangona.usermanagement;

import com.babbangona.commons.library.config.UserDetailsServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = {
		"com.babbangona.commons.library",
		"com.babbangona.usermanagement"
})
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
