<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Compromissos Cadastrados</title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<div class="container">
			<div class="row mt-5">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th>ID</th>
							<th>Funcionário</th>
							<th>Agenda</th>
							<th>Data</th>
							<th>Horário</th>
							<th class="text-end">Ação</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="compromissos">
							<tr>
								<td>${rowid}</td>
								<td>${nomeFuncionario}</td>
								<td>${nomeAgenda}</td>
								<td>${data}</td>
								<td>${horario}</td>
								<td class="text-end">
									<s:url action="editarCompromissos" var="editar">
										<s:param name="compromissoVo.rowid" value="rowid"></s:param>
									</s:url>
									<a href="${editar}" class="btn btn-warning text-white">Editar</a>

									<a href="#" class="btn btn-danger btn-excluir" data-bs-toggle="modal" data-bs-target="#confirmarExclusao" data-rowid="${rowid}">
										Excluir
									</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>

					<tfoot class="table-secondary">
						<tr>
							<td colspan="6">
								<s:url action="novoCompromissos" var="novo"/>
								<a href="${novo}" class="btn btn-success">Novo</a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>

		<div class="modal fade" id="confirmarExclusao" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Confirmação de Exclusão</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		      	<span>Deseja excluir este compromisso?</span>
		      </div>
		      <div class="modal-footer">
	        	<a class="btn btn-secondary" data-bs-dismiss="modal">Não</a>
				<s:a id="excluir" class="btn btn-primary" style="width: 75px;">Sim</s:a>
		      </div>
		    </div>
		  </div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<script>
			var linksExcluir = document.querySelectorAll('.btn-excluir');
			linksExcluir.forEach(function(link){
				link.addEventListener('click', function(){
					var rowid = this.getAttribute('data-rowid');
					document.getElementById('excluir').setAttribute(
							'href',
							'excluirCompromissos.action?compromissoVo.rowid=' + rowid
					);
				});
			});
		</script>
	</body>
</html>