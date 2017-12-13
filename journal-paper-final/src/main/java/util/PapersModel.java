package util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import errors.PaperNaoEncontradoException;
import errors.JournalNaoEncontradoException;
import models.Paper;
import service.PaperService;


public class PapersModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final int COLUNA_ID = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_VALOR = 2;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 9;
	
	private static PaperService paperService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	paperService = (PaperService)fabrica.getBean ("PaperService");
    }

    private Map<Integer, Paper> cache;
    private int rowIndexAnterior = 0;
    private Integer qtd;
    private long ident;
    private String nome;
    
    public PapersModel(long ident){	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Paper>(NUMERO_DE_LINHAS_POR_PAGINA * 2 * 100 / 75 + NUMERO_DE_LINHAS_POR_PAGINA / 3);
		this.ident = ident;
		this.nome = "";
	}
    
    public PapersModel(String nome){	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Paper>(NUMERO_DE_LINHAS_POR_PAGINA * 2 * 100 / 75 + NUMERO_DE_LINHAS_POR_PAGINA / 3);
		this.ident = -1;
		this.nome = nome;
	}

	public void setNomePaper(String nome) {
		this.nome = nome;
	}

	public String getColumnName(int c){
		if(c == COLUNA_ID) return "ID";
		if(c == COLUNA_NOME) return "Nome";
		if(c == COLUNA_VALOR) return "Valor";
		return null;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		if(qtd == null)
			if(this.nome.equals(""))
				qtd = (int)paperService.recuperaQuantPeloIdDoJournal(ident);
			else
				qtd = (int)paperService.recuperaQuantPeloNome(this.nome);
		return qtd;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{   
		if (!cache.containsKey(rowIndex)) 
		{	
			System.out.println(">>>>>>>>>>>> cache não tem rowIndex = " + rowIndex);
			System.out.println(">>>>>>>>>>>> tamanho = " + this.cache.size());
				
			if(cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 2))
			{	
				System.out.println(">>>>>>>>>>>>>>>>>...... Vai limpar .......");
				
				cache.clear();
				
				if(rowIndex >= rowIndexAnterior) {

					System.out.println("1Como estamos navegando para baixo e como a linha " + rowIndex + " não foi encontrada no cache (que foi apagado), vamos recuperar do banco 40 linhas com deslocamento de " + (rowIndex - 19));
					
					List<Paper> resultados;
					if(this.nome.equals(""))
						resultados = paperService
							.recuperaPeloID(ident, rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);
					else
						resultados = paperService
							.recuperaPapersPeloNome(this.nome, rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);
					for (int j = 0; j < resultados.size(); j++) 
					{	Paper paper = resultados.get(j);
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, paper);
					}
				}
				else{
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0) inicio = 0;
					
					System.out.println("2Como estamos navegando para cima e como a linha " + rowIndex + " não foi encontrada no cache (que foi apagado), vamos recuperar do banco 40 linhas com deslocamento de " + inicio);
					
					List<Paper> resultados;
					if(this.nome.equals(""))
						resultados = paperService
							.recuperaPeloID(ident, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					else
						resultados = paperService
							.recuperaPapersPeloNome(this.nome, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					System.out.println("resultados = " + resultados.size());
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Paper paper = resultados.get(j);
						cache.put(inicio + j, paper);
					}
				}
				
				System.out.println(">>>>>>>>>>>>>>>>>...... Tamanho = " + this.cache.size());
			}
			else{
				if(rowIndex >= rowIndexAnterior) {
					System.out.println("3Como estamos navegando para baixo e a linha " + rowIndex + " não foi encontrada, vamos recuperar do banco 40 linhas com um deslocamento de " + rowIndex);
					
					List<Paper> resultados;
					if(this.nome.equals(""))
						resultados = paperService
							.recuperaPeloID(ident, rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					else
						resultados = paperService
							.recuperaPapersPeloNome(this.nome, rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Paper paper = resultados.get(j);
						cache.put(rowIndex + j, paper);
					}
				}
				else{
					int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					if (inicio < 0) inicio = 0;
				
					System.out.println("4Como estamos navegando para cima e a linha " 
							+ rowIndex + " não foi encontrada, vamos recuperar do banco "
									+ "40 linhas com inicio a partir de " + inicio);
					
					List<Paper> resultados;
					if(this.nome.equals(""))
						resultados = paperService
							.recuperaPeloID(ident, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					else
						resultados = paperService
							.recuperaPapersPeloNome(this.nome, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					System.out.println("resultados = " + resultados.size());
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Paper paper = resultados.get(j);
						cache.put(inicio + j, paper);
					}
				}
			}
        }

		rowIndexAnterior = rowIndex;
        
		Paper paper = cache.get(rowIndex);

		if(columnIndex == COLUNA_ID)
			return paper.getId();
		else if (columnIndex == COLUNA_NOME)
			return paper.getNome();
		else if (columnIndex == COLUNA_VALOR)
			return paper.getValor();
		else
			return null;
	}
	
	// Para que os campos booleanos sejam renderizados como check box.
	// Neste caso, não há campo boleano.
	public Class<?> getColumnClass(int c)
	{
		Class<?> classe = null;
		if(c == COLUNA_ID) classe = Long.class;
		if(c == COLUNA_NOME) classe = String.class;
		if(c == COLUNA_VALOR) classe = Integer.class;
		return classe;
	}
}


