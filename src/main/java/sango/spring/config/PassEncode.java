package sango.spring.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncode {

	public static void main(String[] args) {
		String encoded = new BCryptPasswordEncoder().encode("");
		System.out.println(encoded);
	}

}
