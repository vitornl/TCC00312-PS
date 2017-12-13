package errors;

import anotations.ConstraintViolada;
import anotations.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
@ConstraintViolada(nome="FILME_CLIENTE_FK")
public class JournalComPapersException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public JournalComPapersException()
	{	super();
	}

	public JournalComPapersException(String msg)
	{	super(msg);
	}
}	