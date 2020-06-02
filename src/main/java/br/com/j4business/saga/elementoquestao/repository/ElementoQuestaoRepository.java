package br.com.j4business.saga.elementoquestao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestao;

@Repository("elementoQuestaoRepository")
public interface ElementoQuestaoRepository extends PagingAndSortingRepository<ElementoQuestao, Long>{

/*	 @Query("SELECT ea FROM ElementoQuestao ea where ea.questao.questaoPK = :id") 
	    List<ElementoQuestao> findByQuestaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.questao p WHERE p = :questao")
	public List<ElementoQuestao> findByQuestao(@Param("questao")Questao questao);

	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.questao p INNER JOIN ep.elemento e WHERE p = :questao AND s = :elemento")
	public ElementoQuestao findByElementoAndQuestao( @Param("elemento") Elemento elemento, @Param("questao")Questao questao);
	
	@Query("SELECT ep FROM ElementoQuestao ep")
	public List<ElementoQuestao> findElementoQuestaoAll(Pageable pageable);

	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.elemento e WHERE e.elementoPK = :elementoPK")
	public List<ElementoQuestao> findByElementoPK(@Param("elementoPK")long elementoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.questao p WHERE p.questaoPK = :questaoPK")
	public List<ElementoQuestao> findByQuestaoPK(@Param("questaoPK")long questaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.elemento e WHERE e.elementoNome like :elementoNome%")
	public List<ElementoQuestao> findByElementoNome(@Param("elementoNome")String elementoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.questao p WHERE p.questaoNome like :questaoNome%")
	public List<ElementoQuestao> findByQuestaoNome(@Param("questaoNome")String questaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.elemento e WHERE e.elementoNome like :elementoNome%")
	public List<ElementoQuestao> findByElementoNome(@Param("elementoNome")String elementoNome);
	
	@Query("SELECT ep FROM ElementoQuestao ep INNER JOIN ep.questao p WHERE p.questaoNome like :questaoNome%")
	public List<ElementoQuestao> findByQuestaoNome(@Param("questaoNome")String questaoNome);
	

}