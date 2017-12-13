package dao;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import errors.ObjetoNaoEncontradoException;

public interface DaoGenerico<T, PK extends Serializable>{
	@Transactional
	T inclui(T obj);

	@Transactional
    void altera(T obj);

	@Transactional
    void exclui(T obj);

    T getPorId(PK id) throws ObjetoNaoEncontradoException;

    T getPorIdComLock(PK id) throws ObjetoNaoEncontradoException;
}
