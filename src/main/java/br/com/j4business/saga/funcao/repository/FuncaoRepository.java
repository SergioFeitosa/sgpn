package br.com.j4business.saga.funcao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.funcao.model.Funcao;


@Repository("funcaoRepository")

public interface FuncaoRepository extends JpaRepository<Funcao, Long>{

	@Query("SELECT p FROM Funcao p WHERE p.funcaoDescricao like :funcaoDescricao%")
	public List<Funcao> findByFuncaoDescricao(@Param("funcaoDescricao")String funcaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Funcao p WHERE p.funcaoNome like :funcaoNome%")
	public List<Funcao> findByFuncaoNome(@Param("funcaoNome")String funcaoNome,Pageable pageable);

	@Query("SELECT p FROM Funcao p WHERE p.funcaoDescricao like :funcaoDescricao%")
	public List<Funcao> findByFuncaoDescricao(@Param("funcaoDescricao")String funcaoDescricao);
	
	@Query("SELECT p FROM Funcao p WHERE p.funcaoNome like :funcaoNome%")
	public List<Funcao> findByFuncaoNome(@Param("funcaoNome")String funcaoNome);

}