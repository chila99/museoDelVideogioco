package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

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
public class Opera {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NonNull
	private String titolo;
	
	@Column(nullable = false)
	@NonNull
	private Long annoRealizzazione;
	@Column(length = 10000)
	private String descrizione;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@NonNull
	private List<Collezione> collezioni;
	
	@ManyToOne
	@NonNull
	private Artista artista;
	
	@Column(nullable = false, length = 64)
    private String foto;
	
	@Transient
    public String getPhotosImagePath() {
        if (foto == null || id == null) return null;
         
        return "/opera-foto/" + id + "/" + foto;
    }
	
}
