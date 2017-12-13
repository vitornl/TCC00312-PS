package aspecto;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DataIntegrityViolationException;

import anotations.RoleAdmin;
import errors.ViolacaoDeConstraintDesconhecidaException;
import permissions.PermissoesSingleton;

@Aspect
public class AspectoRole {
	@Pointcut("call(* service.*.*(..))")
	public void verificaPermissaoAround() {}

	@Around("verificaPermissaoAround()")
	public Object verificaPermissaoAround(ProceedingJoinPoint joinPoint) throws Throwable {	
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		
		ArrayList<String> permissoes = new ArrayList<String>();
		permissoes.add("ROLE_ADMIN");
		permissoes.add("ROLE_USER1");
		PermissoesSingleton perm = PermissoesSingleton.getPermissoesSingleton();
		perm.setPermissoes(permissoes);
		
		if(method.isAnnotationPresent(RoleAdmin.class)) {
			if(permissoes)
		}
		
		if(!temPermissao) {
			throw new excessaoderole;
		}
		
		return joinPoint.proceed();
		
	}
}
