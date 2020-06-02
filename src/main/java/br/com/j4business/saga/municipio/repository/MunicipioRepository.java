package br.com.j4business.saga.municipio.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.municipio.model.Municipio;


@Repository("municipioRepository")

public interface MunicipioRepository extends JpaRepository<Municipio, Long>{

	@Query("SELECT p FROM Municipio p WHERE p.municipioNome like :municipioNome%")
	public List<Municipio> findByMunicipioNome(@Param("municipioNome")String municipioNome);

	@Query("SELECT p FROM Municipio p WHERE p.municipioNome like :municipioNome%")
	public List<Municipio> findByMunicipioNome(@Param("municipioNome")String municipioNome,Pageable pageable);

	@Query("SELECT p FROM Municipio p WHERE p.municipioCEP like :municipioCEP%")
	public List<Municipio> findByMunicipioCEP(@Param("municipioCEP")String municipioCEP,Pageable pageable);
	
	@Query("SELECT p FROM Municipio p WHERE p.municipioCEP like :municipioCEP%")
	public List<Municipio> findByMunicipioCEP(@Param("municipioCEP")String municipioCEP);
	
	@Query("SELECT me FROM Municipio me INNER JOIN me.estado e WHERE e.estadoSigla like :municipioUF%")
	public List<Municipio> findByMunicipioUF(@Param("municipioUF")String municipioUF,Pageable pageable);
	
	@Query("SELECT me FROM Municipio me INNER JOIN me.estado e WHERE e.estadoSigla like :municipioUF%")
	public List<Municipio> findByMunicipioUF(@Param("municipioUF")String municipioUF);
	
}