package aspecto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.reflections.Reflections;
import org.springframework.dao.DataIntegrityViolationException;

import errors.ViolacaoDeConstraintDesconhecidaException;
import permissions.PermissoesSingleton;

@Aspect
public class AspectoParaTratamentoDeViolacaoDeConstraintV2 
{
	private static Map<String, Class<?>> map = new HashMap<String, Class<?>>();
	private static List<String> listaDeNomesDeConstraints;
	
	static{
		Reflections reflections = new Reflections("errors");

		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(anotations.ConstraintViolada.class);	

		for(Class<?> classe : annotated){
			map.put(classe.getAnnotation(anotations.ConstraintViolada.class).nome(), classe);
		}
		
		listaDeNomesDeConstraints = new ArrayList<String>(map.keySet());		
	}
	
	@Pointcut("call(* service.*.*(..))")
	public void traduzExcecaoAround() {}

	@Around("traduzExcecaoAround()")
	public Object traduzExcecaoAround(ProceedingJoinPoint joinPoint) throws Throwable {	
		try{	
			return joinPoint.proceed();
		}
		catch(org.springframework.dao.DataAccessException e){	
			System.out.println("1. " + e.getClass().getName());
			
			Throwable t = e;
			
			if( t instanceof DataIntegrityViolationException){	
				System.out.println("2. " + e.getClass().getName());

				t = t.getCause();
				while (t != null && !(t instanceof SQLException)){
					t = t.getCause();
				}
				
				String msg = (t.getMessage() != null) ? t.getMessage() : "";
				
				for(String nomeDeConstraint : listaDeNomesDeConstraints){
					if(msg.indexOf(nomeDeConstraint) != -1){
						throw (Exception)map.get(nomeDeConstraint).newInstance();
					}
				}
				throw new ViolacaoDeConstraintDesconhecidaException
					("A operação não foi realizada em função da violação de uma restrição no banco da dados.");
			}
			else{	
				System.out.println("3. " + e.getClass().getName());
				throw e;
			}
		}
	}
}