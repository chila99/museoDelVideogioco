package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Artista {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NonNull
	private String nome;
	
	@Column(nullable = false)
	@NonNull
	private String cognome;
	
	@Column(nullable = false)
	@NonNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@Column(nullable = false)
	@NonNull
	private String luogoNascita;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataMorte;
	private String luogoMorte;
	
	@Column(nullable = false)
	@NonNull
	private String nazionalita;
	
	@OneToMany(mappedBy = "artista", cascade = CascadeType.REMOVE)
	private List<Opera> opereRealizzate;
	
}
