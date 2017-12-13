package util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import errors.JournalNaoEncontradoException;
import models.Journal;
import service.JournalService;

public class JournalsModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final int COLUNA_ID = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_VOLUME = 2;
	public static final int COLUNA_VALOR = 3;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 9;
	
	private static JournalService journalService;
	
    static{
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	journalService = (JournalService)fabrica.getBean ("JournalService");
    }

    private Map<Integer, Journal> cache;
    private int rowIndexAnterior = 0;
    private Integer qtd;
    private String nome;
    
    public JournalsModel(){	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Journal>(NUMERO_DE_LINHAS_POR_PAGINA * 2 * 100 / 75 + NUMERO_DE_LINHAS_POR_PAGINA / 3);
		this.nome = "";
    }
    
    public JournalsModel(String nome){	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Journal>(NUMERO_DE_LINHAS_POR_PAGINA * 2 * 100 / 75 + NUMERO_DE_LINHAS_POR_PAGINA / 3);
		this.nome = nome;
    }
 
	public void setNomeJournal(String nome) {
		this.nome = nome;
	}
	
	public String getColumnName(int c){
		if(c == COLUNA_ID) return "ID";
		if(c == COLUNA_NOME) return "Nome";
		if(c == COLUNA_VOLUME) return "Volume";
		if(c == COLUNA_VALOR) return "Valor";
		return null;
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		if(qtd == null)
			if(nome.equals(""))
				qtd = (int)journalService.recuperaQtdTotal();
			else
				qtd = (int)journalService.recuperaQtdPeloNome(this.nome);
		
		return qtd;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex){   
		if (!cache.containsKey(rowIndex)) 
		{	
			System.out.println(">>>>>>>>>>>> cache não tem rowIndex = " + rowIndex);
			System.out.println(">>>>>>>>>>>> tamanho = " + this.cache.size());
				
			if(cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 2)){	
				System.out.println(">>>>>>>>>>>>>>>>>...... Vai limpar .......");
				
				cache.clear();
				
				if(rowIndex >= rowIndexAnterior) {
					System.out.println("1Como estamos navegando para baixo e como a linha " + rowIndex + " não foi encontrada no cache (que foi apagado), vamos recuperar do banco 40 linhas com deslocamento de " + (rowIndex - 19));
					
					List<Journal> resultados;
					if(this.nome.equals("")) {
						resultados = journalService
								.recuperaNaOrdem( rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);
					}
					else {
						resultados = journalService
								.recuperaPorNome( rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2, this.nome);
					}
					for (int j = 0; j < resultados.size(); j++) {
						Journal journal = resultados.get(j);
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, journal);
					}
				}
				else
				{
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0) inicio = 0;
					System.out.println("2Como estamos navegando para cima e como a linha " + rowIndex + " não foi encontrada no cache (que foi apagado), vamos recuperar do banco 40 linhas com deslocamento de " + inicio);
					
					List<Journal> resultados;
					if(this.nome.equals("")) {
						resultados = journalService
								.recuperaNaOrdem( inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					}
					else {
						resultados = journalService
								.recuperaPorNome( inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2, this.nome);
					}
					
					System.out.println("resultados = " + resultados.size());
					
					for (int j = 0; j < resultados.size(); j++) {
						Journal journal = resultados.get(j);
						cache.put(inicio + j, journal);
					}
				}
				
				System.out.println(">>>>>>>>>>>>>>>>>...... Tamanho = " + this.cache.size());
			}
			else
			{
				if(rowIndex >= rowIndexAnterior) {

					System.out.println("3Como estamos navegando para baixo e a linha " + rowIndex + " não foi encontrada, vamos recuperar do banco 40 linhas com um deslocamento de " + rowIndex);
					
					List<Journal> resultados;
					if(this.nome.equals("")) {
						resultados = journalService
								.recuperaNaOrdem( rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					}
					else {
						resultados = journalService
								.recuperaPorNome( rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2, this.nome);
					}
					
					for (int j = 0; j < resultados.size(); j++) {
						Journal journal = resultados.get(j);
						cache.put(rowIndex + j, journal);
					}
				}
				else{
					int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					if (inicio < 0) inicio = 0;

					System.out.println("4Como estamos navegando para cima e a linha " 
							+ rowIndex + " não foi encontrada, vamos recuperar do banco "
									+ "40 linhas com inicio a partir de " + inicio);
					
					List<Journal> resultados;
					if(this.nome.equals("")) {
						resultados = journalService
								.recuperaNaOrdem( inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					}
					else {
						resultados = journalService
								.recuperaPorNome( inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2, this.nome);
					}
					
					System.out.println("resultados = " + resultados.size());
					
					for (int j = 0; j < resultados.size(); j++) {
						Journal journal = resultados.get(j);
						cache.put(inicio + j, journal);
					}
				}
			}
        }

		rowIndexAnterior = rowIndex;
        
		Journal journal = cache.get(rowIndex);

		if(columnIndex == COLUNA_ID)
			return journal.getId();
		else if (columnIndex == COLUNA_NOME)
			return journal.getNome();
		else if (columnIndex == COLUNA_VOLUME)
			return journal.getVolume();
		else if (columnIndex == COLUNA_VALOR)
			return journal.getValor();
		else
			return null;
	}
	
	// Para que os campos booleanos sejam renderizados como check box.
	// Neste caso, não há campo boleano.
	public Class<?> getColumnClass(int c){
		Class<?> classe = null;
		if(c == COLUNA_ID) classe = Long.class;
		if(c == COLUNA_NOME) classe = String.class;
		if(c == COLUNA_VOLUME) classe = Integer.class;
		if(c == COLUNA_VALOR) classe = Float.class;
		return classe;
	}
}


