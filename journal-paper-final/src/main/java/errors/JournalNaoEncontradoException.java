package errors;

import anotations.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class JournalNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public JournalNaoEncontradoException(String msg)
	{	super(msg);
	}
}	