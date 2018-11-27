package br.com.faetec.model;

public enum Turno {
	
	M(0, "Manhã"), T(1, "Tarde"), N(2, "Noite");
	
	
	private int id;
	private String descricao;

	Turno(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
