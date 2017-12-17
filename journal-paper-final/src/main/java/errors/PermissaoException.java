package errors;

import anotations.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class PermissaoException  extends Exception {
private final static long serialVersionUID = 1;
	
	public PermissaoException()
	{	super();
	}

	public PermissaoException(String msg)
	{	super(msg);
	}

}
