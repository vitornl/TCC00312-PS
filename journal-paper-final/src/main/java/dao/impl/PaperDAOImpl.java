package dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import dao.PaperDAO;
import models.Paper;

@Repository
public abstract class PaperDAOImpl 	extends JPADaoGenerico<Paper, Long> implements PaperDAO{
	public PaperDAOImpl() {	
		super(Paper.class);		
	}
	
	@SuppressWarnings("unchecked")
	public final List<Paper> recuperaPeloID(long resp, int deslocamento,  int linhasPorPagina){	
		List<Paper> papers = em
			.createQuery("select c from Paper c where journal_id = :resp order by c.nome asc")
			.setParameter("resp", resp)
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();
		
		return papers;
	
	}
	public final long recuperaQuantPeloIdDoJournal(long resp) {
		long qtd = (Long) em.createQuery("select count(c) from Paper c where c.journal.id = :resp")
				.setParameter("resp", resp)
				.getSingleResult();
      return qtd;
	}
	
	public final long recuperaQuantPeloNome(String resp) {
		long qtd = (Long) em.createQuery("select count(c) from Paper c where " +
					"c.nome like '%' || LOWER(:resp) || '%' or c.nome like '%' || UPPER(:resp) || '%'")
				.setParameter("resp", resp)
				.getSingleResult();
      return qtd;
	}
	
	
	@SuppressWarnings("unchecked")
	public final List<Paper> recuperaPapersPeloIdDoJournal(long resp){	
		List<Paper> papers = em
			.createQuery("select c from Paper c where c.journal.id = :resp")
			.setParameter("resp", resp)
			.getResultList();

		return papers;
	}
	
	@SuppressWarnings("unchecked")
	public final List<Paper> recuperaPapersPeloNome(String resp, int deslocamento, int linhasPorPagina){	
		List<Paper> papers = em
			.createQuery("select c from Paper c where " + 
					"c.nome like '%' || LOWER(:resp) || '%' or c.nome like '%' || UPPER(:resp) || '%'")
			.setParameter("resp", resp)
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();

		return papers;
	}
	
	
}
