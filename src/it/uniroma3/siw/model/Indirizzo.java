package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Indirizzo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String via;
	
	@Column(nullable = false)
	private Long numeroCivico;
	
	@Column(nullable = false)
	private String comune;
	
	@Column(nullable = false)
	private Long cap;
	
	@Column(nullable = false)
	private String provincia;
}
