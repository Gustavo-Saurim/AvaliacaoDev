package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao{
	
	public void insertCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder("INSERT INTO compromisso (cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) values (?, ?, ?, ?)");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setLong(i++, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(i++, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setDate(i++, Date.valueOf(compromissoVo.getData()));
			ps.setTime(i++, Time.valueOf(compromissoVo.getHorario() + ":00"));
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCompromisso(String rowid) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso WHERE rowid = ?");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setString(i++, rowid);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder("UPDATE compromisso SET cd_funcionario = ?, cd_agenda = ?, dt_compromisso = ?, hr_compromisso = ? WHERE rowid = ?");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setLong(i++, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(i++, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setDate(i++, Date.valueOf(compromissoVo.getData()));
			ps.setTime(i++, Time.valueOf(compromissoVo.getHorario() + ":00"));
			ps.setString(i++, compromissoVo.getRowid());
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CompromissoVo> findAllCompromissos(){
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, cd_funcionario funcionario, cd_agenda agenda, dt_compromisso data, hr_compromisso horario FROM compromisso");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()){
			
			CompromissoVo vo = null;
			List<CompromissoVo> compromissos = new ArrayList<>();
			while (rs.next()) {
				vo = new CompromissoVo();
				vo.setRowid(rs.getString("id"));
				vo.setCodigoFuncionario(rs.getString("funcionario"));
				vo.setCodigoAgenda(rs.getString("agenda"));
				vo.setData(rs.getString("data"));
				String horario = rs.getString("horario");
				vo.setHorario(horario != null && horario.length() >= 5 ? horario.substring(0, 5) : horario);
				
				compromissos.add(vo);
			}
			return compromissos;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	public CompromissoVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, cd_funcionario funcionario, cd_agenda agenda, dt_compromisso data, hr_compromisso horario FROM compromisso ")
				.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setInt(i,  codigo);
			
			try(ResultSet rs = ps.executeQuery()){
				CompromissoVo vo = null;
				
				while (rs.next()) {
					vo = new CompromissoVo();
					vo.setRowid(rs.getString("id"));
					vo.setCodigoFuncionario(rs.getString("funcionario"));
					vo.setCodigoAgenda(rs.getString("agenda"));
					vo.setData(rs.getString("data"));
					String horario = rs.getString("horario");
					vo.setHorario(horario != null && horario.length() >= 5 ? horario.substring(0, 5) : horario);
				}
				return vo;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}