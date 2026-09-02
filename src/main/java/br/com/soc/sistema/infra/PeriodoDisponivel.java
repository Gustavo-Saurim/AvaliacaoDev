package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum PeriodoDisponivel {
	MANHA("1", "Manhã"),
	TARDE("2", "Tarde"),
	AMBOS("3", "Ambos");
	
	private String codigo;
	private String descricao;
	private final static Map<String, PeriodoDisponivel> opcoes = new HashMap<>();
	
	static {
		Arrays.asList(PeriodoDisponivel.values())
		.forEach(
				opcao -> opcoes.put(opcao.getCodigo(), opcao)
		);
	}
	
	private PeriodoDisponivel(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static PeriodoDisponivel buscarPor(String codigo) throws IllegalArgumentException {
		if(codigo == null)
			throw new IllegalArgumentException("informe um codigo valido");
		
		return getOpcao(codigo)
				.orElseThrow(() -> new BusinessException("Codigo informado nao existe"));
	}
	
	private static Optional<PeriodoDisponivel> getOpcao(String codigo){
		return Optional.ofNullable(opcoes.get(codigo));
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
