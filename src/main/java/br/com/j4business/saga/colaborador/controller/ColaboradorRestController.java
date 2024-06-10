package br.com.j4business.saga.colaborador.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.j4business.saga.colaborador.message.ColaboradorResponse;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;

@RestController
public class ColaboradorRestController {

	@Autowired
	private ColaboradorService colaboradorService;

	@GetMapping(path = "/colaboradores")
	public List<Colaborador> getAllColaboradors() {
		return colaboradorService.getColaboradorAll();
	}

	@GetMapping(value = "/colaborador/{colaboradorPK}")
	public Colaborador getColaboradorById(@PathVariable("colaboradorPK") long colaboradorPK) {
		return colaboradorService.getColaboradorByColaboradorPK(colaboradorPK);
	}

	@DeleteMapping(value = "/deleteColaborador/{colaboradorPK}")
	@ResponseBody
	public String deleteColaborador(@PathVariable("colaboradorPK") Long colaboradorPK) {
		colaboradorService.delete(colaboradorPK);
		return colaboradorPK.toString();

	}

	@GetMapping(value = "/getColaboradorAll")
	public ColaboradorResponse getColaboradorAll() {

		List<Colaborador> colaboradorList = new ArrayList<Colaborador>();
		colaboradorList = colaboradorService.getColaboradorAll();
		ColaboradorResponse response = new ColaboradorResponse("Done", colaboradorList);

		return response;
	}

}
