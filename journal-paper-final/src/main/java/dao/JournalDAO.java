package dao;

import java.util.List;
import java.util.Set;

import anotations.RecuperaConjunto;
import anotations.RecuperaLista;
import anotations.RecuperaObjeto;
import errors.ObjetoNaoEncontradoException;
import models.Journal;

public interface JournalDAO extends DaoGenerico<Journal, Long>{   
	@RecuperaObjeto
	Journal recuperaUmJournalEPapers(long numero) 
		throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Journal> recuperaListaDeJournals();
	
	@RecuperaLista
	List<Journal> recuperaListaDeJournalsEPapers();

	@RecuperaConjunto
	Set<Journal> recuperaConjuntoDeJournalsEPapers();
		
	long recuperaQtdTotal();
	
	long recuperaQtdPeloNome(String resp);

	List<Journal> recuperaNaOrdem(int deslocamento, int linhasPorPagina);
	
	List<Journal> recuperaPorNome(int deslocamento, int linhasPorPagina, String resp);
	
}
