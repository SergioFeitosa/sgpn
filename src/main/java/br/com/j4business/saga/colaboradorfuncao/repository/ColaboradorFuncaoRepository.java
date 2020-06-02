package br.com.j4business.saga.colaboradorfuncao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncao;

@Repository("colaboradorFuncaoRepository")
public interface ColaboradorFuncaoRepository extends PagingAndSortingRepository<ColaboradorFuncao, Long>{

/*	 @Query("SELECT ea FROM ColaboradorFuncao ea where ea.funcao.funcaoPK = :id") 
	    List<ColaboradorFuncao> findByFuncaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.funcao p WHERE p = :funcao")
	public List<ColaboradorFuncao> findByFuncao(@Param("funcao")Funcao funcao);

	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.funcao p INNER JOIN ep.colaborador e WHERE p = :funcao AND s = :colaborador")
	public ColaboradorFuncao findByColaboradorAndFuncao( @Param("colaborador") Colaborador colaborador, @Param("funcao")Funcao funcao);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep")
	public List<ColaboradorFuncao> findColaboradorFuncaoAll(Pageable pageable);

	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.colaborador e WHERE e.pessoaPK = :colaboradorPK")
	public List<ColaboradorFuncao> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.funcao p WHERE p.funcaoPK = :funcaoPK")
	public List<ColaboradorFuncao> findByFuncaoPK(@Param("funcaoPK")long funcaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorFuncao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.funcao p WHERE p.funcaoNome like :funcaoNome%")
	public List<ColaboradorFuncao> findByFuncaoNome(@Param("funcaoNome")String funcaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorFuncao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT ep FROM ColaboradorFuncao ep INNER JOIN ep.funcao p WHERE p.funcaoNome like :funcaoNome%")
	public List<ColaboradorFuncao> findByFuncaoNome(@Param("funcaoNome")String funcaoNome);
	

}