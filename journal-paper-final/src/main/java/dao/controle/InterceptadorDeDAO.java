package dao.controle;

import java.lang.reflect.Method;

import anotations.RecuperaConjunto;
import anotations.RecuperaLista;
import anotations.RecuperaObjeto;
import anotations.RecuperaUltimoOuPrimeiro;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import dao.impl.JPADaoGenerico;

public class InterceptadorDeDAO implements MethodInterceptor {
	public Object intercept (Object objeto, Method metodo, Object[] args, MethodProxy metodoDoProxy)throws Throwable  {
		// O símbolo ? representa um tipo desconhecido.
        JPADaoGenerico<?,?> daoGenerico = (JPADaoGenerico<?,?>)objeto;

        if(metodo.isAnnotationPresent(RecuperaLista.class)){	
        	// O método buscaLista() retorna um List
        	return daoGenerico.buscaLista(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaConjunto.class)){	
        	// O método buscaConjunto() retorna um Set
        	return daoGenerico.buscaConjunto(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaUltimoOuPrimeiro.class)) {	
        	// O método buscaUltimoOuPrimeiro() retorna um Objeto (Entidade)
        	return daoGenerico.buscaUltimoOuPrimeiro(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaObjeto.class)) {	
        	// O método busca() retorna um Objeto (Entidade)
        	return daoGenerico.busca(metodo, args);
        }
        else  {  	
        	//throw new InfraestruturaException("Um método não final deixou de ser anotado");
        	return metodoDoProxy.invokeSuper(objeto, args);
        }
    }
}
