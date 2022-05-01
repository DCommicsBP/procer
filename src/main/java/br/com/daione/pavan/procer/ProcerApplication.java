package br.com.daione.pavan.procer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Cadastro de Pessoas", version = "1.0",
		description = "API Utilizada para cadastro de pessoas. "))
@SpringBootApplication
public class ProcerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcerApplication.class, args);
	}

}
