package contactura.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import contactura.model.ContacturaUser;
import contactura.repository.ContacturaUserRepository;

@CrossOrigin()
@RestController
@RequestMapping({"/user"})
public class ContacturaUserController {
	@Autowired
	private ContacturaUserRepository repository;
	
	@RequestMapping("/login")
	@PostMapping
	public String login(HttpServletRequest request){
		String token = request.getHeader("Authorization")
				.substring("Basic".length()).trim();
		return token;
		//return ResponseEntity.ok().body("Usuário Logado com Sucesso!\nToken: " + token);
		//return () -> new String(Base64.getDecoder()
		//		.decode(token)).split(":")[1];
	}
	
	//Lista todos os usuários
	@GetMapping

	public List findAll() {
		return repository.findAll();
	}
	
	//Retorna usuário por id
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id){
		return repository.findById(id)
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//Criar novo usuário
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ContacturaUser create(@RequestBody ContacturaUser user) {
		user.setPassword(criptografarSenha(user.getPassword()));
		return repository.save(user);
	}
	
	//Atualizar usuário
	@PutMapping(value = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody ContacturaUser user){
		return repository.findById(id)
				.map(record -> {
					record.setName(user.getName());
					record.setUsername(user.getUsername());
					record.setPassword(criptografarSenha(user.getPassword()));
					record.setAdmin(false);
					ContacturaUser update = repository.save(record);
					return ResponseEntity.ok().body(update);					
				}).orElse(ResponseEntity.notFound().build());
	}
	
	//Deletar usuário
	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().body("Deletado com sucesso!");	
				}).orElse(ResponseEntity.notFound().build());
	}

	// Criptografar Senha
	private String criptografarSenha(String password) {
		BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
		String passwordParaCriptografar = passwordEnconder.encode(password);
		return passwordParaCriptografar;
	}
	
	
	
	
	
	
	
	

}
