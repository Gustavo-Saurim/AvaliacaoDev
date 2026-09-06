<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cadastro de Compromisso</title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">

		<div class="container">
			<s:form action="/novoCompromissos.action">
				<s:actionerror cssClass="alert alert-danger mt-3"/>

				<div class="card mt-3">
					<div class="card-header">
						<div class="row">
							<div class="col-sm-5">
								<s:url action="todosCompromissos" var="todos"/>
								<a href="${todos}" class="btn btn-success">Compromissos</a>
							</div>
							<div class="col-sm">
								<h5 class="card-title">Novo Compromisso</h5>
							</div>
						</div>
					</div>

					<div class="card-body">
						<input type="hidden" name="compromissoVo.rowid" value="<s:property value="compromissoVo.rowid"/>"/>

						<div class="row align-items-center">
							<label for="funcionario" class="col-sm-2 col-form-label">Funcionário:</label>
							<div class="col-sm-5">
								<s:select
									cssClass="form-select"
									id="funcionario"
									name="compromissoVo.codigoFuncionario"
									list="funcionarios"
									listKey="rowid"
									listValueKey="nome"
									headerKey=""
									headerValue="Escolha..."
								/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="agenda" class="col-sm-2 col-form-label">Agenda:</label>
							<div class="col-sm-5">
								<s:select
									cssClass="form-select"
									id="agenda"
									name="compromissoVo.codigoAgenda"
									list="agendas"
									listKey="rowid"
									listValueKey="nome"
									headerKey=""
									headerValue="Escolha..."
								/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="data" class="col-sm-2 col-form-label">Data:</label>
							<div class="col-sm-3">
								<input type="date" class="form-control" id="data"
									name="compromissoVo.data"
									value="<s:property value="compromissoVo.data"/>"/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="horario" class="col-sm-2 col-form-label">Horário:</label>
							<div class="col-sm-3">
								<input type="time" class="form-control" id="horario"
									name="compromissoVo.horario"
									value="<s:property value="compromissoVo.horario"/>"/>
							</div>
						</div>
					</div>

					<div class="card-footer">
						<button class="btn btn-primary">Salvar</button>
						<button type="reset" class="btn btn-secondary">Limpar</button>
					</div>
				</div>
			</s:form>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>