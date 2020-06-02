package br.com.j4business.saga.empresa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.empresa.model.Empresa;


@Repository("empresaRepository")

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	@Query("SELECT p FROM Empresa p WHERE p.empresaNomeFantasia like :empresaNomeFantasia%")
	public List<Empresa> findByEmpresaNomeFantasia(@Param("empresaNomeFantasia")String empresaNomeFantasia,Pageable pageable);
	
	@Query("SELECT p FROM Empresa p WHERE p.pessoaNome like :empresaNome%")
	public List<Empresa> findByEmpresaNome(@Param("empresaNome")String empresaNome,Pageable pageable);

	@Query("SELECT p FROM Empresa p WHERE p.empresaNomeFantasia like :empresaNomeFantasia%")
	public List<Empresa> findByEmpresaNomeFantasia(@Param("empresaNomeFantasia")String empresaNomeFantasia);
	
	@Query("SELECT p FROM Empresa p WHERE p.pessoaNome like :empresaNome%")
	public List<Empresa> findByEmpresaNome(@Param("empresaNome")String empresaNome);

}