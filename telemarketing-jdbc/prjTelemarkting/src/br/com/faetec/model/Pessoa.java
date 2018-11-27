package br.com.faetec.model;

import java.util.List;

public class Pessoa {
	private Integer id;
	private String nome;
	private String sobreNome;
	private String cpf;
	private String email;
	private String pai;
	private String mae;
	private String cep;
	private String endereco;
	private String bairro;
	private String celular;
	private String residencial;
	private String celularPai;
	private String celularMae;
	private Boolean ativo;
	private List<Ligacao> ligacoes;
	private Ligacao ligacao;
		

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getResidencial() {
		return residencial;
	}

	public void setResidencial(String residencial) {
		this.residencial = residencial;
	}

	public String getCelularPai() {
		return celularPai;
	}

	public void setCelularPai(String celularPai) {
		this.celularPai = celularPai;
	}

	public String getCelularMae() {
		return celularMae;
	}

	public void setCelularMae(String celularMae) {
		this.celularMae = celularMae;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Ligacao> getLigacoes() {
		return ligacoes;
	}

	public void setLigacoes(List<Ligacao> ligacoes) {
		this.ligacoes = ligacoes;
	}

	public Ligacao getLigacao() {
		return ligacao;
	}

	public void setLigacao(Ligacao ligacao) {
		this.ligacao = ligacao;
	}

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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", sobreNome="
				+ sobreNome + ", cpf=" + cpf + ", email=" + email + ", pai="
				+ pai + ", mae=" + mae + ", cep=" + cep + ", endereco="
				+ endereco + ", bairro=" + bairro + ", celular=" + celular
				+ ", residencial=" + residencial + ", celularPai=" + celularPai
				+ ", celularMae=" + celularMae + ", ativo=" + ativo
				+ ", ligacoes=" + ligacoes + ", ligacao=" + ligacao + "]";
	}

}
