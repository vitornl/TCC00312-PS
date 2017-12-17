package service;

import java.util.List;
import dao.JournalDAO;
import errors.ObjetoNaoEncontradoException;
import errors.PermissaoException;
import errors.JournalComPapersException;
import errors.JournalNaoEncontradoException;
import models.Journal;
import org.springframework.transaction.annotation.Transactional;

import anotations.ROLE_ADMIN;
import anotations.ROLE_USER1;

public class JournalService{	
	
	private JournalDAO journalDAO = null;
	
	public void setJournalDAO(JournalDAO journalDAO){	
		this.journalDAO = journalDAO; 
	}
	
	//Retornando o objeto 'Journal' atual
	public JournalDAO getJournal(){
         return this.journalDAO ; 
	}
	 
	//Adicionando um objeto 'Journal'
	@Transactional
	@ROLE_ADMIN
	public long inclui(Journal umJournal) throws PermissaoException {	
		return journalDAO.inclui(umJournal).getId();
	}

	
	//Modificando um objeto 'Journal'
	@Transactional
	@ROLE_ADMIN
	@ROLE_USER1
	public void altera(Journal umJournal)throws JournalNaoEncontradoException, PermissaoException {	
		try	{
			journalDAO.getPorIdComLock(umJournal.getId());
			journalDAO.altera(umJournal);
		} 
		catch(ObjetoNaoEncontradoException e){
			throw new JournalNaoEncontradoException("Journal não encontrado");
		}
	}


	@Transactional
	@ROLE_ADMIN
	public void exclui(Journal umJournal) throws JournalNaoEncontradoException, JournalComPapersException, PermissaoException{
		try{	
			Journal journal = journalDAO.getPorId(umJournal.getId());
			if(journal.getPapers().size() > 0){
				throw new JournalComPapersException("Este journal possui papers cadastrados e não pode ser removido");
			}
			journalDAO.exclui(journal);
		}
		catch(ObjetoNaoEncontradoException e){	
			throw new JournalNaoEncontradoException("Journal não encontrado");
		}
	}
	
	//Retorna um objeto 'Journal' dado um ID
	public Journal recuperaUmJournal(long id)throws JournalNaoEncontradoException{	
		try	{	
			return journalDAO.getPorId(id);
		} 
		catch(ObjetoNaoEncontradoException e){	
			throw new JournalNaoEncontradoException("Journal não encontrado");
		}
	}
	
	//Retorna um objeto 'Journal' dado um ID e também os objetos 'Paper' associados
	public Journal recuperaUmJournalEPapers(long id) throws JournalNaoEncontradoException{	
		try	{	
			return journalDAO.recuperaUmJournalEPapers(id);
		} 
		catch(ObjetoNaoEncontradoException e){	
			throw new JournalNaoEncontradoException("Journal não encontrado");
		}
	}
	
	//Recupera uma lista com os objetos 'Journal' juntamente com os objetos 'Paper' associados
	public List<Journal> recuperaListaDeJournalsEPapers(){	
		return journalDAO.recuperaListaDeJournalsEPapers();
	}

	public long recuperaQtdTotal() {	
		return journalDAO.recuperaQtdTotal();
	}
	
	public long recuperaQtdPeloNome(String resp) {	
		return journalDAO.recuperaQtdPeloNome(resp);
	}

	public  List<Journal> recuperaNaOrdem( int deslocamento, int linhasPorPagina) {	
		List<Journal> p = journalDAO.recuperaNaOrdem( deslocamento, linhasPorPagina);
		return p;
	}
	
	public  List<Journal> recuperaPorNome( int deslocamento, int linhasPorPagina, String resp) {	
		List<Journal> p = journalDAO.recuperaPorNome( deslocamento, linhasPorPagina, resp);
		return p;
	}
}