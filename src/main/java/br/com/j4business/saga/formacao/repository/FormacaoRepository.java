package br.com.j4business.saga.formacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.formacao.model.Formacao;


@Repository("formacaoRepository")

public interface FormacaoRepository extends JpaRepository<Formacao, Long>{

	@Query("SELECT p FROM Formacao p WHERE p.formacaoDescricao like :formacaoDescricao%")
	public List<Formacao> findByFormacaoDescricao(@Param("formacaoDescricao")String formacaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Formacao p WHERE p.formacaoNome like :formacaoNome%")
	public List<Formacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome,Pageable pageable);

	@Query("SELECT p FROM Formacao p WHERE p.formacaoDescricao like :formacaoDescricao%")
	public List<Formacao> findByFormacaoDescricao(@Param("formacaoDescricao")String formacaoDescricao);
	
	@Query("SELECT p FROM Formacao p WHERE p.formacaoNome like :formacaoNome%")
	public List<Formacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome);

}