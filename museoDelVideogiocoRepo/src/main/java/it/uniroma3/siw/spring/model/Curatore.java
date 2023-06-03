package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Curatore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NonNull
	private String nome;
	
	@Column(nullable =  false)
	@NonNull
	private String cognome;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataDiNascita;
	private String luogoDiNascita;
	
	@Column(nullable =  false, unique = true)
	@NonNull
	private String email;
	private String numeroTelefono;
	
	@Column(nullable =  false, unique = true)
	@NonNull
	private String matricola;
}
