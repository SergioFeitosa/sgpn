package br.com.j4business.saga.treinamentovideo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideo;

@Repository("treinamentoVideoRepository")
public interface TreinamentoVideoRepository extends PagingAndSortingRepository<TreinamentoVideo, Long>{

/*	 @Query("SELECT ea FROM TreinamentoVideo ea where ea.video.videoPK = :id") 
	    List<TreinamentoVideo> findByVideoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.video p WHERE p = :video")
	public List<TreinamentoVideo> findByVideo(@Param("video")Video video);

	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.video p INNER JOIN ep.treinamento e WHERE p = :video AND s = :treinamento")
	public TreinamentoVideo findByTreinamentoAndVideo( @Param("treinamento") Treinamento treinamento, @Param("video")Video video);
	
	@Query("SELECT ep FROM TreinamentoVideo ep")
	public List<TreinamentoVideo> findTreinamentoVideoAll(Pageable pageable);

	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoVideo> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoVideo> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK and ep.treinamentoVideoStatus = 'ATIVO'")
	public List<TreinamentoVideo> findTreinamentoVideoAtivoByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.video p WHERE p.videoPK = :videoPK")
	public List<TreinamentoVideo> findByVideoPK(@Param("videoPK")long videoPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoVideo> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<TreinamentoVideo> findByVideoNome(@Param("videoNome")String videoNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoVideo> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);
	
	@Query("SELECT ep FROM TreinamentoVideo ep INNER JOIN ep.video p WHERE p.videoNome like :videoNome%")
	public List<TreinamentoVideo> findByVideoNome(@Param("videoNome")String videoNome);
	

}