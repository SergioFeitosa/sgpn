package br.com.j4business.saga.ocorrenciaatendimento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimento;

@Repository("ocorrenciaAtendimentoRepository")
public interface OcorrenciaAtendimentoRepository extends JpaRepository<OcorrenciaAtendimento, Long>{

/*	 @Query("SELECT ea FROM OcorrenciaAtendimento ea where ea.atendimento.atendimentoPK = :id") 
	    List<OcorrenciaAtendimento> findByAtendimentoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM OcorrenciaAtendimento pa INNER JOIN pa.atendimento p WHERE p = :atendimento")
	public List<OcorrenciaAtendimento> findByAtendimento(@Param("atendimento")Atendimento atendimento);

	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.atendimento p INNER JOIN ps.ocorrencia s WHERE p = :atendimento AND s = :ocorrencia")
	public OcorrenciaAtendimento findByOcorrenciaAndAtendimento( @Param("ocorrencia") Ocorrencia ocorrencia, @Param("atendimento")Atendimento atendimento);
	
	@Query("SELECT pa FROM OcorrenciaAtendimento pa")
	public List<OcorrenciaAtendimento> findOcorrenciaAtendimentoAll(Pageable pageable);

	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.ocorrencia s WHERE s.ocorrenciaNome like :ocorrenciaNome%")
	public List<OcorrenciaAtendimento> findByOcorrenciaNome(@Param("ocorrenciaNome")String ocorrenciaNome,Pageable pageable);
	
	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.atendimento p WHERE p.atendimentoNome like :atendimentoNome%")
	public List<OcorrenciaAtendimento> findByAtendimentoNome(@Param("atendimentoNome")String atendimentoNome,Pageable pageable);
	
	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.ocorrencia s WHERE s.ocorrenciaNome like :ocorrenciaNome%")
	public List<OcorrenciaAtendimento> findByOcorrenciaNome(@Param("ocorrenciaNome")String ocorrenciaNome);
	
	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.atendimento p WHERE p.atendimentoNome like :atendimentoNome%")
	public List<OcorrenciaAtendimento> findByAtendimentoNome(@Param("atendimentoNome")String atendimentoNome);
	
	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.ocorrencia s WHERE s.ocorrenciaPK = :ocorrenciaPK")
	public List<OcorrenciaAtendimento> findByOcorrenciaPK(@Param("ocorrenciaPK")long ocorrenciaPK,Pageable pageable);
	
	@Query("SELECT ps FROM OcorrenciaAtendimento ps INNER JOIN ps.atendimento p WHERE p.atendimentoPK = :atendimentoPK")
	public List<OcorrenciaAtendimento> findByAtendimentoPK(@Param("atendimentoPK")long atendimentoPK,Pageable pageable);
	

}