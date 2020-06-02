package br.com.j4business.saga.video.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.video.model.Video;


@Repository("videoRepository")

public interface VideoRepository extends JpaRepository<Video, Long>{

	@Query("SELECT p FROM Video p WHERE p.videoDescricao like :videoDescricao%")
	public List<Video> findByVideoDescricao(@Param("videoDescricao")String videoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Video p WHERE p.videoNome like :videoNome%")
	public List<Video> findByVideoNome(@Param("videoNome")String videoNome,Pageable pageable);

	@Query("SELECT p FROM Video p WHERE p.videoDescricao like :videoDescricao%")
	public List<Video> findByVideoDescricao(@Param("videoDescricao")String videoDescricao);
	
	@Query("SELECT p FROM Video p WHERE p.videoNome like :videoNome%")
	public List<Video> findByVideoNome(@Param("videoNome")String videoNome);

}