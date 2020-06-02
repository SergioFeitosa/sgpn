package br.com.j4business.saga.processoatividade.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;

@Repository("processoAtividadeRepository")
public interface ProcessoAtividadeRepository extends PagingAndSortingRepository<ProcessoAtividade, Long>{

/*	 @Query("SELECT ea FROM ProcessoAtividade ea where ea.processo.processoPK = :id") 
	    List<ProcessoAtividade> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.processo p WHERE p = :processo")
	public List<ProcessoAtividade> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.processo p INNER JOIN pa.atividade a WHERE p = :processo AND a = :atividade")
	public ProcessoAtividade findByProcessoAndAtividade(@Param("processo")Processo processo, @Param("atividade") Atividade atividade);
	
	@Query("SELECT pa FROM ProcessoAtividade pa")
	public List<ProcessoAtividade> findProcessoAtividadeAll(Pageable pageable);

	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.atividade a WHERE a.atividadeNome like :atividadeNome%")
	public List<ProcessoAtividade> findByAtividadeNome(@Param("atividadeNome")String atividadeNome,Pageable pageable);
	
	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.processo p WHERE p.processoNome like :processoNome%")
	public List<ProcessoAtividade> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.processo p WHERE p.processoPK = :processoPK")
	public List<ProcessoAtividade> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT pa FROM ProcessoAtividade pa INNER JOIN pa.processo p WHERE p.processoPK = :processoPK")
	public List<ProcessoAtividade> findByProcessoPK(@Param("processoPK")long processoPK);
	


}