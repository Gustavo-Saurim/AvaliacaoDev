package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoAction extends Action{
	
	private List<CompromissoVo> compromissos = new ArrayList<>();
	private List<FuncionarioVo> funcionarios = new ArrayList<>();
	private List<AgendaVo> agendas = new ArrayList<>();
	
	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();
	
	private CompromissoVo compromissoVo = new CompromissoVo();
	
	public CompromissoAction() {
		funcionarios.addAll(funcionarioBusiness.trazerTodosOsFuncionarios());
		agendas.addAll(agendaBusiness.trazerTodasAsAgendas());
	}
	
	private void buscarNomeFuncionario(CompromissoVo compromisso) {
		for (FuncionarioVo funcionario : funcionarios) {
			if(funcionario.getRowid().equals(compromisso.getCodigoFuncionario())) {
				compromisso.setNomeFuncionario(funcionario.getNome());
				return;
			}
		}
	}
	
	private void buscarNomeAgenda(CompromissoVo compromisso) {
		for (AgendaVo agenda : agendas) {
			if(agenda.getRowid().equals(compromisso.getCodigoAgenda())) {
				compromisso.setNomeAgenda(agenda.getNome());
				return;
			}
		}
	}
	
	public String todos() {
		compromissos.addAll(business.trazerTodosOsCompromissos());
		
		for (CompromissoVo compromisso : compromissos) {
			buscarNomeFuncionario(compromisso);
			buscarNomeAgenda(compromisso);
		}
		
		return SUCCESS;
	}
	
	public String novo() {
		if(isBlank(compromissoVo.getCodigoFuncionario())
				|| isBlank(compromissoVo.getCodigoAgenda())
				|| isBlank(compromissoVo.getData())
				|| isBlank(compromissoVo.getHorario()))
			return INPUT;
		
		try {
			if(compromissoVo.getRowid() == null || compromissoVo.getRowid().isEmpty())
				business.salvarCompromisso(compromissoVo);
			else
				business.atualizarCompromisso(compromissoVo);
		}catch (BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		
		return REDIRECT;
	}
	
	private boolean isBlank(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
	
	public String editar() {
		if(compromissoVo.getRowid() == null)
			return REDIRECT;
		
		compromissoVo = business.buscarCompromissoPor(compromissoVo.getRowid());
		
		return INPUT;
	}
	
	public String excluir() {
		if(compromissoVo.getRowid() == null)
			return REDIRECT;
		
		business.excluirCompromisso(compromissoVo.getRowid());
		
		return REDIRECT;
	}
	
	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}
	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}
	public List<FuncionarioVo> getFuncionarios() {
		return funcionarios;
	}
	public List<AgendaVo> getAgendas() {
		return agendas;
	}
	public CompromissoVo getCompromissoVo() {
		return compromissoVo;
	}
	public void setCompromissoVo(CompromissoVo compromissoVo) {
		this.compromissoVo = compromissoVo;
	}
}


















