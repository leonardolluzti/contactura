package contactura.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Criação de construtores padrões
@AllArgsConstructor
//Cria o construtor
@NoArgsConstructor
//Cria nossos gets e sets, assim como nossos hashcodes, etc 
@Data
//Definir quando uma classe é uma entidade espelho do banco de dados
@Entity
public class Contactura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phone;
	

}