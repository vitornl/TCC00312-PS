package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(
		{	@NamedQuery
			(	name = "Paper.recuperaListaDePapers",
				query = "select f from Paper f order by f.id"
			),
			@NamedQuery
			(	name = "Paper.recuperaUltimoPaper",
				query = "select f from Paper f where f.journal = ?1 order by f.id desc"
			),
			@NamedQuery
			(	name = "Paper.recuperaUmPaperComJournal",
				query = "select f from Paper f left outer join fetch f.journal where f.id = ?1"
			)
		})
		
@Entity
@Table(name="Paper")
@SequenceGenerator(name="SEQUENCIA2", 
		           sequenceName="SEQ_PAPER",
		           allocationSize=1)

public class Paper{	
	private Long id;
	private String nome;
	private float valor;
		
	private Journal journal;
	public Paper(){}

	public Paper(String nome, float valor, Journal journal) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.journal = journal;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA2")
	@Column(name="ID")
	public Long getId(){
		return id;
	}

	public String getNome(){	
		return nome.toUpperCase();
	}
	
	public float getValor(){
		return valor;
	}


	@SuppressWarnings("unused")
	private void setId(Long id){
		this.id = id;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	
	public void setValor(float valor) {
		this.valor = valor;
	}

	// ********* Métodos para Associações *********

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JOURNAL_ID")
	public Journal getJournal(){
		return this.journal;
	}
	
	public void setJournal(Journal journal){
		this.journal = journal;
	}
	
	@Override
	public String toString(){
		return "(" + id + ") " + nome;
	}
}	