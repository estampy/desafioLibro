package com.estanislao.desafioLibro;

import com.estanislao.desafioLibro.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioLibroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioLibroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();
	}
}
