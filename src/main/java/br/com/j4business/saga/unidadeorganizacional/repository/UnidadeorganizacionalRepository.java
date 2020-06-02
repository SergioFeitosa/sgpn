package br.com.j4business.saga.unidadeorganizacional.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;


@Repository("unidadeorganizacionalRepository")

public interface UnidadeorganizacionalRepository extends JpaRepository<Unidadeorganizacional, Long>{

	@Query("SELECT p FROM Unidadeorganizacional p WHERE p.unidadeorganizacionalDescricao like :unidadeorganizacionalDescricao%")
	public List<Unidadeorganizacional> findByUnidadeorganizacionalDescricao(@Param("unidadeorganizacionalDescricao")String unidadeorganizacionalDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Unidadeorganizacional p WHERE p.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<Unidadeorganizacional> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome,Pageable pageable);

	@Query("SELECT p FROM Unidadeorganizacional p WHERE p.unidadeorganizacionalDescricao like :unidadeorganizacionalDescricao%")
	public List<Unidadeorganizacional> findByUnidadeorganizacionalDescricao(@Param("unidadeorganizacionalDescricao")String unidadeorganizacionalDescricao);
	
	@Query("SELECT p FROM Unidadeorganizacional p WHERE p.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<Unidadeorganizacional> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome);

}