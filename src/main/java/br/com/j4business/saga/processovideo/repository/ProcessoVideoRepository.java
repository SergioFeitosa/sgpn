package br.com.j4business.saga.processovideo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processovideo.model.ProcessoVideo;

@Repository("processoVideoRepository")
public interface ProcessoVideoRepository extends JpaRepository<ProcessoVideo, Long>{

/*	 @Query("SELECT ea FROM ProcessoVideo ea where ea.video.videoPK = :id") 
	    List<ProcessoVideo> findByVideoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.video p WHERE p = :video")
	public List<ProcessoVideo> findByVideo(@Param("video")Video video);

	@Query("SELECT pv FROM ProcessoVideo pv INNER JOIN pv.video v INNER JOIN pv.processo p WHERE v = :video AND p = :processo")
	public ProcessoVideo findByProcessoAndVideo( @Param("processo") Processo processo, @Param("video")Video video);
	
	@Query("SELECT ep FROM ProcessoVideo ep")
	public List<ProcessoVideo> findProcessoVideoAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoVideo> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoVideo> findByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK and ep.processoVideoStatus = 'ATIVO'")
	public List<ProcessoVideo> findProcessoVideoAtivoByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.video p WHERE p.videoPK = :videoPK")
	public List<ProcessoVideo> findByVideoPK(@Param("videoPK")long videoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoVideo> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<ProcessoVideo> findByVideoNome(@Param("videoNome")String videoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoVideo> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<ProcessoVideo> findByVideoNome(@Param("videoNome")String videoNome);
	

}