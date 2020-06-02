package br.com.j4business.saga.colaboradortreinamento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamento;

@Repository("colaboradorTreinamentoRepository")
public interface ColaboradorTreinamentoRepository extends PagingAndSortingRepository<ColaboradorTreinamento, Long>{

/*	 @Query("SELECT ea FROM ColaboradorTreinamento ea where ea.treinamento.treinamentoPK = :id") 
	    List<ColaboradorTreinamento> findByTreinamentoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p WHERE p = :treinamento")
	public List<ColaboradorTreinamento> findByTreinamento(@Param("treinamento")Treinamento treinamento);

	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p INNER JOIN ep.colaborador e WHERE p = :treinamento AND s = :colaborador")
	public ColaboradorTreinamento findByColaboradorAndTreinamento( @Param("colaborador") Colaborador colaborador, @Param("treinamento")Treinamento treinamento);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep")
	public List<ColaboradorTreinamento> findColaboradorTreinamentoAll(Pageable pageable);

	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.colaborador e WHERE e.pessoaPK = :colaboradorPK")
	public List<ColaboradorTreinamento> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoPK = :treinamentoPK")
	public List<ColaboradorTreinamento> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoPK = :treinamentoPK")
	public List<ColaboradorTreinamento> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorTreinamento> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<ColaboradorTreinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorTreinamento> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT ep FROM ColaboradorTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<ColaboradorTreinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);
	

}