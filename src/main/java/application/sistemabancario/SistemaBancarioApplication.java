package application.sistemabancario;

import controller.SistemaBancarioController;
import dtos.Conta;
import dtos.Pessoa;
import dtos.Transacao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("repository")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = SistemaBancarioController.class)
@EntityScan(basePackageClasses = {Conta.class, Transacao.class, Pessoa.class})
public class SistemaBancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaBancarioApplication.class, args);
	}

}
