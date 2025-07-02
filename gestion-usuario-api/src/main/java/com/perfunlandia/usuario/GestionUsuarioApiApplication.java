package com.perfunlandia.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.perfunlandia.usuario"})
public class GestionUsuarioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionUsuarioApiApplication.class, args);
	}

}
