package dao;

import java.util.List;

import anotations.RecuperaLista;
import anotations.RecuperaUltimoOuPrimeiro;
import errors.ObjetoNaoEncontradoException;
import models.Paper;
import models.Journal;

public interface PaperDAO extends DaoGenerico<Paper, Long>{	
	@RecuperaLista
	List<Paper> recuperaListaDePapers();
	
	@RecuperaUltimoOuPrimeiro
	Paper recuperaUltimoPaper(Journal funcionario)throws ObjetoNaoEncontradoException;

	long recuperaQuantPeloIdDoJournal(long ident);
	
	long recuperaQuantPeloNome(String resp);

	List<Paper> recuperaPeloID(long resp, int deslocamento, int linhasPorPagina);

	List<Paper> recuperaPapersPeloIdDoJournal(long resp); 
	
	List<Paper> recuperaPapersPeloNome(String resp, int deslocamento, int linhasPorPagina); 
}
