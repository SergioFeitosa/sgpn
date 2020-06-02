package br.com.j4business.saga.servico.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.servico.model.Servico;


@Repository("servicoRepository")

public interface ServicoRepository extends JpaRepository<Servico, Long>{

	@Query("SELECT p FROM Servico p WHERE p.servicoDescricao like :servicoDescricao%")
	public List<Servico> findByServicoDescricao(@Param("servicoDescricao")String servicoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Servico p WHERE p.servicoNome like :servicoNome%")
	public List<Servico> findByServicoNome(@Param("servicoNome")String servicoNome,Pageable pageable);

	@Query("SELECT p FROM Servico p WHERE p.servicoDescricao like :servicoDescricao%")
	public List<Servico> findByServicoDescricao(@Param("servicoDescricao")String servicoDescricao);
	
	@Query("SELECT p FROM Servico p WHERE p.servicoNome like :servicoNome%")
	public List<Servico> findByServicoNome(@Param("servicoNome")String servicoNome);

}