package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaAction extends Action{
	
	private List<AgendaVo> agendas = new ArrayList<>();
	private AgendaBusiness business = new AgendaBusiness();
	private AgendaVo agendaVo = new AgendaVo();
	
	public String todos() {
		agendas.addAll(business.trazerTodasAsAgendas());
		
		return SUCCESS;
	}
	
	public String novo() {
		if(agendaVo.getNome() == null)
			return INPUT;
		try {
			if(agendaVo.getRowid() == null || agendaVo.getRowid().isEmpty())
				business.salvarAgenda(agendaVo);
			else
				business.atualizarAgenda(agendaVo);
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		
		return REDIRECT;
	}
	
	public String editar() {
		if(agendaVo.getRowid() == null)
			return REDIRECT;
		
		agendaVo = business.buscarAgendaPor(agendaVo.getRowid());
		
		return INPUT;
	}
	
	public String excluir() {
		if(agendaVo.getRowid() == null)
			return REDIRECT;
		
		try {
			business.excluirAgenda(agendaVo.getRowid());
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			todos();
			return SUCCESS;
		}
		
		return REDIRECT;
	}
	
	public List<PeriodoDisponivel> getListaPeriodos(){
		return Arrays.asList(PeriodoDisponivel.values());
	}
	
	public List<AgendaVo> getAgendas(){
		return agendas;
	}
	
	public void setAgendas(List<AgendaVo> agendas) {
		this.agendas = agendas;
	}
	
	public AgendaVo getAgendaVo() {
		return agendaVo;
	}
	
	public void setAgendaVo(AgendaVo agendaVo) {
		this.agendaVo = agendaVo;
	}
}
