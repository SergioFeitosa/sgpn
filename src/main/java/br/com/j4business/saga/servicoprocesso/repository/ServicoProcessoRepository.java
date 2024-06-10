package br.com.j4business.saga.servicoprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;

@Repository("servicoProcessoRepository")
public interface ServicoProcessoRepository extends JpaRepository<ServicoProcesso, Long>{

/*	 @Query("SELECT ea FROM ServicoProcesso ea where ea.processo.processoPK = :id") 
	    List<ServicoProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM ServicoProcesso pa INNER JOIN pa.processo p WHERE p = :processo")
	public List<ServicoProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.processo p INNER JOIN ps.servico s WHERE p = :processo AND s = :servico")
	public ServicoProcesso findByServicoAndProcesso( @Param("servico") Servico servico, @Param("processo")Processo processo);
	
	@Query("SELECT pa FROM ServicoProcesso pa")
	public List<ServicoProcesso> findServicoProcessoAll(Pageable pageable);

	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.servico s WHERE s.servicoNome like :servicoNome%")
	public List<ServicoProcesso> findByServicoNome(@Param("servicoNome")String servicoNome,Pageable pageable);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.processo p WHERE p.processoNome like :processoNome%")
	public List<ServicoProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.servico s WHERE s.servicoNome like :servicoNome%")
	public List<ServicoProcesso> findByServicoNome(@Param("servicoNome")String servicoNome);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.processo p WHERE p.processoNome like :processoNome%")
	public List<ServicoProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.servico s WHERE s.servicoPK = :servicoPK")
	public List<ServicoProcesso> findByServicoPK(@Param("servicoPK")long servicoPK,Pageable pageable);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.servico s WHERE s.servicoPK = :servicoPK")
	public List<ServicoProcesso> findByServicoPK(@Param("servicoPK")long servicoPK);
	
	@Query("SELECT ps FROM ServicoProcesso ps INNER JOIN ps.processo p WHERE p.processoPK = :processoPK")
	public List<ServicoProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	

}