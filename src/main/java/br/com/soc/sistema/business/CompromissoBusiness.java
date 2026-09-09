package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;

public class CompromissoBusiness {
	
	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private CompromissoDao dao;
	private AgendaDao agendaDao = new AgendaDao();
	
	public CompromissoBusiness() {
		this.dao = new CompromissoDao();
	}
	
	public List<CompromissoVo> trazerCompromissosPorPeriodo(String dataInicial, String dataFinal) {
		try {
			return dao.findByPeriodo(dataInicial, dataFinal);
		}catch(Exception e) {
			throw new BusinessException("Nao foi possivel gerar o relatorio");
		}
	}
	
	public List<CompromissoVo> trazerTodosOsCompromissos(){
		return dao.findAllCompromissos();
	}
	
	public void salvarCompromisso(CompromissoVo compromissoVo) {
		try {
			ValidarDisponibilidadeAgenda(compromissoVo);
			dao.insertCompromisso(compromissoVo);
		}catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do registro");
		}
	}
	
	public void excluirCompromisso(String rowid) {
		try {
			dao.deleteCompromisso(rowid);
		}catch(Exception e) {
			throw new BusinessException("Nao foi possivel realizar a exclusao do registro");
		}
	}
	
	public void atualizarCompromisso(CompromissoVo compromissoVo) {
		try {
			ValidarDisponibilidadeAgenda(compromissoVo);
			dao.updateCompromisso(compromissoVo);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Nao foi possivel realizar a atualizacao do registro");
		}
	}
	
	private void ValidarDisponibilidadeAgenda(CompromissoVo compromissoVo) {
		AgendaVo agenda = agendaDao.findByCodigo(Integer.parseInt(compromissoVo.getCodigoAgenda()));
		
		PeriodoDisponivel periodo = PeriodoDisponivel.buscarPor(agenda.getPeriodoDisponivel());
		
		int hora = Integer.parseInt(compromissoVo.getHorario().split(":")[0]);
		boolean ehManha = hora < 13;
		
		boolean disponivel =
				periodo == PeriodoDisponivel.AMBOS ||
				(periodo == PeriodoDisponivel.MANHA && ehManha) ||
				(periodo == PeriodoDisponivel.TARDE && !ehManha);
		
		if(!disponivel)
			throw new BusinessException("O horario informado esta fora da disponibilidade da agenda selecionada (" + periodo.getDescricao() + ")");
	}
	
	public CompromissoVo buscarCompromissoPor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		}catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}
}
