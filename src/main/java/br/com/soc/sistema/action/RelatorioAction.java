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

public class RelatorioAction extends Action{
	
	private String dataInicial;
	private String dataFinal;
	private List<CompromissoVo> compromissos = new ArrayList<>();
	private List<FuncionarioVo> funcionarios = new ArrayList<>();
	private List<AgendaVo> agendas = new ArrayList<>();
	
	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();
	
	public RelatorioAction() {
		funcionarios.addAll(funcionarioBusiness.trazerTodosOsFuncionarios());
		agendas.addAll(agendaBusiness.trazerTodasAsAgendas());
	}
	
	public String filtrar() {
		if(dataInicial == null || dataInicial.isEmpty()
				|| dataFinal == null || dataFinal.isEmpty()) {
			addActionError("Informe a data inicial e a data final");
			return INPUT;
		}
		
		try {
			compromissos.addAll(business.trazerCompromissosPorPeriodo(dataInicial, dataFinal));
		}catch (BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		
		for (CompromissoVo compromisso : compromissos) {
			buscarNomeFuncionario(compromisso);
			buscarNomeAgenda(compromisso);
		}
		return SUCCESS;
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
	
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}
}
