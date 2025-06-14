package br.edu.atitus.api_sample.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_sample.dtos.SignupDTO;
import br.edu.atitus.api_sample.entities.UserEntity;
import br.edu.atitus.api_sample.entities.UserType;
import br.edu.atitus.api_sample.services.UserService;

@RestController
@RequestMapping("/auth")
// O /auth ficara responsavel pelo signin e signup 
public class AuthController {
	private final UserService service;
	
	//Injeção de dependencida via método construtor
	// O responstavel por criar um objeto Auth controller é o Spring
	//
	public AuthController(UserService service) {
		super();
		this.service = service;
	}

	//Criando um metodo de Entiddade de Resposta
	// Como parametro de entrada no signup, vamos colocar a classe que criamos para tratar os dados como um objeto 
	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(@RequestBody SignupDTO dto) throws Exception {
		// Cria uma entidade (objeto) com os dados que foi recebido
		UserEntity user = new UserEntity();
		user.setType(UserType.Common);
		BeanUtils.copyProperties(dto, user);
		service.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	//Manipulador de Exceções
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handlerException(Exception ex) {
		String message = ex.getMessage().replaceAll("\r\n","");
		return ResponseEntity.badRequest().body(message);
		
	}

}
