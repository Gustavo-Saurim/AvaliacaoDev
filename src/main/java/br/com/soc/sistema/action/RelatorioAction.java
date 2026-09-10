package br.com.soc.sistema.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	private InputStream excelStream;
	
	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();
	
	public RelatorioAction() {
		funcionarios.addAll(funcionarioBusiness.trazerTodosOsFuncionarios());
		agendas.addAll(agendaBusiness.trazerTodasAsAgendas());
	}
	
	public String exportarExcel() throws IOException {
		if(dataInicial == null || dataInicial.isEmpty()
				|| dataFinal == null || dataFinal.isEmpty()) {
			addActionError("Informe a data inicial e a data final");
			return INPUT;
		}
		
		compromissos.addAll(business.trazerCompromissosPorPeriodo(dataInicial, dataFinal));
		
		for (CompromissoVo compromisso : compromissos) {
			buscarNomeFuncionario(compromisso);
			buscarNomeAgenda(compromisso);
		}
		
		try(Workbook workbook = new XSSFWorkbook()){
			Sheet sheet = workbook.createSheet("Compromissos");
			
			Row cabecalho = sheet.createRow(0);
			cabecalho.createCell(0).setCellValue("Código funcionário");
			cabecalho.createCell(1).setCellValue("Nome Funcionário");
			cabecalho.createCell(2).setCellValue("Código Agenda");
			cabecalho.createCell(3).setCellValue("Nome Agenda");
			cabecalho.createCell(4).setCellValue("Data");
			cabecalho.createCell(5).setCellValue("Hora");
			
			int numeroLinha = 1;
			for (CompromissoVo compromisso :compromissos) {
				Row linha = sheet.createRow(numeroLinha++);
				linha.createCell(0).setCellValue(compromisso.getCodigoFuncionario());
				linha.createCell(1).setCellValue(compromisso.getNomeFuncionario());
				linha.createCell(2).setCellValue(compromisso.getCodigoAgenda());
				linha.createCell(3).setCellValue(compromisso.getNomeAgenda());
				linha.createCell(4).setCellValue(compromisso.getData());
				linha.createCell(5).setCellValue(compromisso.getHorario());
			}
			
			for (int i = 0; i <= 5; i++) {
				sheet.autoSizeColumn(i);
			}
			
			ByteArrayOutputStream saida = new ByteArrayOutputStream();
			workbook.write(saida);
			excelStream = new ByteArrayInputStream(saida.toByteArray());
		}
		
		return "excel";
	}
	
	public InputStream getExcelStream() {
		return excelStream;
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
