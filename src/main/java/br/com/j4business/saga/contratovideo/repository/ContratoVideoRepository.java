package br.com.j4business.saga.contratovideo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratovideo.model.ContratoVideo;

@Repository("contratoVideoRepository")
public interface ContratoVideoRepository extends PagingAndSortingRepository<ContratoVideo, Long>{

/*	 @Query("SELECT ea FROM ContratoVideo ea where ea.video.videoPK = :id") 
	    List<ContratoVideo> findByVideoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.video p WHERE p = :video")
	public List<ContratoVideo> findByVideo(@Param("video")Video video);

	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.video p INNER JOIN ep.contrato e WHERE p = :video AND s = :contrato")
	public ContratoVideo findByContratoAndVideo( @Param("contrato") Contrato contrato, @Param("video")Video video);
	
	@Query("SELECT ep FROM ContratoVideo ep")
	public List<ContratoVideo> findContratoVideoAll(Pageable pageable);

	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoVideo> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoVideo> findByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK and ep.contratoVideoStatus = 'ATIVO'")
	public List<ContratoVideo> findContratoVideoAtivoByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.video p WHERE p.videoPK = :videoPK")
	public List<ContratoVideo> findByVideoPK(@Param("videoPK")long videoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoVideo> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<ContratoVideo> findByVideoNome(@Param("videoNome")String videoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoVideo> findByContratoNome(@Param("contratoNome")String contratoNome);
	
	@Query("SELECT ep FROM ContratoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<ContratoVideo> findByVideoNome(@Param("videoNome")String videoNome);
	

}