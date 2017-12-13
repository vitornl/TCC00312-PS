package errors;

import anotations.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class PaperNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;

	public PaperNaoEncontradoException(String msg)
	{	super(msg);
	}
}	