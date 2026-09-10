<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Relatório de Compromissos</title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<div class="container">
			<s:actionerror cssClass="alert alert-danger mt-3"/>

			<div class="card mt-3">
				<div class="card-header">
					<h5 class="card-title">Relatório de Compromissos por Período</h5>
				</div>
				<div class="card-body">
					<s:form action="/filtrarRelatorios.action" method="get">
    					<div class="row align-items-end">
					        <div class="col-sm-3">
					            <label for="dataInicial">Data Inicial:</label>
					            <input type="date" class="form-control" id="dataInicial"
					                name="dataInicial" value="<s:property value="dataInicial"/>"/>
					        </div>
					        <div class="col-sm-3">
					            <label for="dataFinal">Data Final:</label>
					            <input type="date" class="form-control" id="dataFinal"
					                name="dataFinal" value="<s:property value="dataFinal"/>"/>
					        </div>
					        <div class="col-sm-2">
					            <button class="btn btn-primary">Pesquisar</button>
					        </div>
					        <div class="col-sm-2">
					            <button type="submit" formaction="exportarExcelRelatorios.action" class="btn btn-success">
					                Exportar Excel
					            </button>
					        </div>
					    </div>
					</s:form>
				</div>
			</div>

			<s:if test="compromissos.size() > 0">
				<div class="row mt-3">
					<table class="table table-light table-striped">
						<thead>
							<tr>
								<th>Cód. Funcionário</th>
								<th>Nome Funcionário</th>
								<th>Cód. Agenda</th>
								<th>Nome Agenda</th>
								<th>Data</th>
								<th>Hora</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="compromissos">
								<tr>
									<td>${codigoFuncionario}</td>
									<td>${nomeFuncionario}</td>
									<td>${codigoAgenda}</td>
									<td>${nomeAgenda}</td>
									<td>${data}</td>
									<td>${horario}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</s:if>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>