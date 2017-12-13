package service;

import java.util.List;
import dao.PaperDAO;
import dao.JournalDAO;
import errors.PaperNaoEncontradoException;
import errors.ObjetoNaoEncontradoException;
import errors.JournalNaoEncontradoException;
import models.Paper;
import models.Journal;
import org.springframework.transaction.annotation.Transactional;

public class PaperService{	
	private JournalDAO journalDAO = null;
	private PaperDAO paperDAO = null;
	
	public void setJournalDAO(JournalDAO journalDAO){
		this.journalDAO = journalDAO;
	}

	public void setPaperDAO(PaperDAO paperDAO){	
		this.paperDAO = paperDAO;
	}

	@Transactional
	public long inclui(Paper umPaper) throws JournalNaoEncontradoException {
		Paper paper = paperDAO.inclui(umPaper);
		return umPaper.getId();
	}	
	
	@Transactional
	public long altera(Paper umPaper) throws JournalNaoEncontradoException {
		paperDAO.altera(umPaper);
		return umPaper.getId();
	}

	@Transactional
	public void exclui(Paper umPaper)throws PaperNaoEncontradoException {
		try	{
			umPaper = paperDAO.getPorId(umPaper.getId());
			paperDAO.exclui(umPaper);
		} 
		catch(ObjetoNaoEncontradoException e){
			throw new PaperNaoEncontradoException("Paper não encontrado.");
		}
	}

	public Paper recuperaUmPaper(long id)throws PaperNaoEncontradoException{
		try	{
			return paperDAO.getPorId(id);
		} 
		catch(ObjetoNaoEncontradoException e){
			throw new PaperNaoEncontradoException("Paper não encontrado");
		}
	}

	public List<Paper> recuperaPapers(){
		return paperDAO.recuperaListaDePapers();
	}

	public long recuperaQuantPeloIdDoJournal(long ident) {
		return paperDAO.recuperaQuantPeloIdDoJournal(ident);	
	}
	
	public long recuperaQuantPeloNome(String resp) {
		return paperDAO.recuperaQuantPeloNome(resp);	
	}

	public List<Paper> recuperaPeloID(long resp, int deslocamento, int linhasPorPagina){
		return paperDAO.recuperaPeloID(resp, deslocamento, linhasPorPagina);
	}

	public List<Paper> recuperaPapersPeloIdDoJournal(long resp) throws ObjetoNaoEncontradoException{	
		return paperDAO.recuperaPapersPeloIdDoJournal(resp);
	}
	
	public List<Paper> recuperaPapersPeloNome(String resp, int deslocamento, int linhasPorPagina){	
		return paperDAO.recuperaPapersPeloNome(resp, deslocamento, linhasPorPagina);
	}
}