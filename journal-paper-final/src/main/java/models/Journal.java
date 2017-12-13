package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@NamedQueries(
		{	@NamedQuery
			(	name = "Journal.recuperaUmJournalEPapers",
				query = "select c from Journal c left outer join fetch c.papers where c.id= ?1"
			),
			@NamedQuery
			(	name = "Journal.recuperaListaDeJournals",
				query = "select c from Journal c order by c.id"
			),
			@NamedQuery
			(	name = "Journal.recuperaListaDeJournalsEPapers",
				query = "select distinct c from Journal c left outer join fetch c.papers order by c.id asc"
			),
			@NamedQuery
			(	name = "Journal.recuperaConjuntoDeJournalsEPapers",
				query = "select c from Journal c left outer join fetch c.papers order by c.id asc"
			)
		})

@Entity
@Table(name="JOURNAL")
@SequenceGenerator(name="SEQUENCIA", 
		           sequenceName="SEQ_JOURNAL",
		           allocationSize=1)

public class Journal
{	
	private Long id;
	private String nome;
	private int volume;
	private float valor;

	private List<Paper> papers = new ArrayList<Paper>();
	
	public Journal(){}

	public Journal(String nome, int volume, float valor) {
		super();
		this.nome = nome;
		this.volume = volume;
		this.valor = valor;
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA")
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome.toUpperCase();
	}
	
	public int getVolume() {	
		return volume;
	}
	
	public float getValor() {
		return valor;
	}
	
	
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void setVolume(int volume) {	
		this.volume = volume;
	}
	
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	
	public String toString(){
		return "(" + id + ") " + nome ;
	}
	
	// ********* Métodos para Associações *********

	@OneToMany(mappedBy = "journal")
	@OrderBy
	public List<Paper> getPapers(){
		return papers;
	}
	
	public void setPapers(List<Paper> papers){
		this.papers = papers;	
	}
}

