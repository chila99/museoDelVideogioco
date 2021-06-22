package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class Collezione {

	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique=true)
	@NonNull
	private String nome;
	
	@Column(nullable = false, length = 10000)
	private String descrizione;
	
	@NonNull
	@ManyToOne
	private Curatore gestore;
	
	@ManyToMany(mappedBy = "collezioni", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Opera> opere;
}
