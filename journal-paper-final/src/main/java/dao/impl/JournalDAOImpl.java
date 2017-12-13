package dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import dao.JournalDAO;
import models.Journal;

@Repository
public abstract class JournalDAOImpl
       extends JPADaoGenerico<Journal, Long> implements JournalDAO 
{   
    //Construtor
	public JournalDAOImpl(){ 	
    	super(Journal.class); 
    }
    	
    public final long recuperaQtdTotal(){	
		long qtd = (Long) em.createQuery("select count(c) from Journal c").getSingleResult();
		return qtd;
	}
    
    public final long recuperaQtdPeloNome(String resp){	
		long qtd = (Long) em
				.createQuery("select count(c) from Journal c where c.nome like '%' || LOWER(:resp) || '%' " +
				 "or nome like '%' || UPPER(:resp) || '%'")
				.setParameter("resp", resp)
				.getSingleResult();
		return qtd;
	}
    
 
	public final  List<Journal> recuperaNaOrdem(int deslocamento,int linhasPorPagina){	
		@SuppressWarnings("unchecked")
		List<Journal> journal = em
			.createQuery("select c from Journal c " +
				     "order by c.id asc")
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();
		
		System.out.println(journal.toString());
		return journal;
	}
	
	public final  List<Journal> recuperaPorNome(int deslocamento,int linhasPorPagina, String resp){
		@SuppressWarnings("unchecked")
		List<Journal> journal = em
			.createQuery("select c from Journal c where c.nome like '%' || LOWER(:resp) || '%' " +
				 "or nome like '%' || UPPER(:resp) || '%' order by c.id asc")
			.setParameter("resp", resp)
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();
		
		System.out.println(journal.toString());
		return journal;
	}
}
