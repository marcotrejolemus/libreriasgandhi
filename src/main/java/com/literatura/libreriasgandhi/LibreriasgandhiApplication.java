package com.literatura.libreriasgandhi;
import com.literatura.libreriasgandhi.principal.Principal;
import com.literatura.libreriasgandhi.repository.iAutorRepository;
import com.literatura.libreriasgandhi.repository.iLibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LibreriasgandhiApplication implements CommandLineRunner {

	@Autowired
	private iLibroRepository libroRepository;
	@Autowired
	private iAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibreriasgandhiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.menu();
	}
}
