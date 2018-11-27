package br.com.faetec.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class Ligacao {
	private Integer id;
	private Time inicio;
	private Time fim;
	private Calendar dataLigacao;
	private String observacoes;
	private Boolean ativo;
	private Pessoa pessoa ;
	
	private List<Curso> cursos;
	//private List<String> turnos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Time getInicio() {
		return inicio;
	}

	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}

	public Time getFim() {
		return fim;
	}

	public void setFim(Time fim) {
		this.fim = fim;
	}

	public Calendar getDataLigacao() {
		return dataLigacao;
	}

	public void setDataLigacao(Calendar dataLigacao) {
		this.dataLigacao = dataLigacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	/*public List<String> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<String> turnos) {
		this.turnos = turnos;
	}
*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ligacao other = (Ligacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "Ligacao [id=" + id + ", inicio=" + inicio + ", fim=" + fim
				+ ", dataLigacao=" + dataLigacao + ", observacoes="
				+ observacoes + ", ativo=" + ativo + ", pessoa=" + pessoa + "]";
	}

}
