package br.com.j4business.saga.colaboradorprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcesso;

@Repository("colaboradorProcessoRepository")
public interface ColaboradorProcessoRepository extends PagingAndSortingRepository<ColaboradorProcesso, Long>{

/*	 @Query("SELECT ea FROM ColaboradorProcesso ea where ea.processo.processoPK = :id") 
	    List<ColaboradorProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<ColaboradorProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.processo p INNER JOIN ep.colaborador e WHERE p = :processo AND s = :colaborador")
	public ColaboradorProcesso findByColaboradorAndProcesso( @Param("colaborador") Colaborador colaborador, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep")
	public List<ColaboradorProcesso> findColaboradorProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.colaborador e WHERE e.pessoaPK = :colaboradorPK")
	public List<ColaboradorProcesso> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<ColaboradorProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorProcesso> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<ColaboradorProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorProcesso> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT ep FROM ColaboradorProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<ColaboradorProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	

}