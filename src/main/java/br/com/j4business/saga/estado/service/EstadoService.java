package br.com.j4business.saga.estado.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.estado.model.Estado;
import br.com.j4business.saga.estado.model.EstadoForm;

@Service
public interface EstadoService {
	
	public List<Estado> getEstadoAll();
	public Page<Estado> getEstadoAll(Pageable pageable);
	public Estado getEstadoByEstadoPK(long estadoPK);
	public Estado create(EstadoForm estadoForm);
	public Estado save(EstadoForm estadoForm);
	public void delete(Long estadoPK);
	
	public Estado converteEstadoForm(EstadoForm estadoForm);
	public EstadoForm converteEstado(Estado estado);
	public EstadoForm converteEstadoView(Estado estado);

	public EstadoForm estadoParametros(EstadoForm estadoForm);

	public List<Estado> getByEstadoNome(String estadoNome,Pageable pageable);
	public List<Estado> getByEstadoNome(String estadoNome);
	public List<Estado> getByEstadoSigla(String estadoSigla,Pageable pageable);
	public List<Estado> getByEstadoSigla(String estadoSigla);

}