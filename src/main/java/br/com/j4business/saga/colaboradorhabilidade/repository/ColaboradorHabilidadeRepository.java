package br.com.j4business.saga.colaboradorhabilidade.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;

@Repository("colaboradorHabilidadeRepository")
public interface ColaboradorHabilidadeRepository extends JpaRepository<ColaboradorHabilidade, Long>{

/*	 @Query("SELECT ea FROM ColaboradorHabilidade ea where ea.habilidade.habilidadePK = :id") 
	    List<ColaboradorHabilidade> findByHabilidadePK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.habilidade p WHERE p = :habilidade")
	public List<ColaboradorHabilidade> findByHabilidade(@Param("habilidade")Habilidade habilidade);

	@Query("SELECT ch FROM ColaboradorHabilidade ch INNER JOIN ch.habilidade h INNER JOIN ch.colaborador c WHERE h = :habilidade AND c = :colaborador")
	public ColaboradorHabilidade findByColaboradorAndHabilidade( @Param("colaborador") Colaborador colaborador, @Param("habilidade")Habilidade habilidade);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep")
	public List<ColaboradorHabilidade> findColaboradorHabilidadeAll(Pageable pageable);

	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.colaborador e WHERE e.pessoaPK = :colaboradorPK")
	public List<ColaboradorHabilidade> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadePK = :habilidadePK")
	public List<ColaboradorHabilidade> findByHabilidadePK(@Param("habilidadePK")long habilidadePK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorHabilidade> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<ColaboradorHabilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorHabilidade> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT ep FROM ColaboradorHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<ColaboradorHabilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome);
	

}