package it.uniroma3.siw.spring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Credentials {
	
	public static final String ROLE_ADMIN = "ADMIN";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Amministratore amministratore;

}
