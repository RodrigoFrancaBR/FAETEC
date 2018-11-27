package br.com.faetec.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.faetec.dao.CursoDao;
import br.com.faetec.dao.PessoaDao;
import br.com.faetec.model.Curso;
import br.com.faetec.model.Pessoa;

@ManagedBean
@SessionScoped
public class PessoaBean {
	private List<Pessoa> pessoas;
	private Pessoa pessoa = new Pessoa();

	public String buscarPessoas() {
		pessoas = PessoaDao.findAll();
		pessoa = new Pessoa();
		return "pessoas?faces-redirect=true";
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void editar(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void salvar() {
		if (null != pessoa.getId()) {
			PessoaDao.update(pessoa);
		} else {
			PessoaDao.save(pessoa);
		}
		pessoa = new Pessoa();
		buscarPessoas();
	}

	public void limpar() {
		pessoa = new Pessoa();
	}

	public void excluir(Pessoa pessoa) {
		PessoaDao.deleteById(pessoa.getId());
		pessoa= new Pessoa();
		buscarPessoas();
	}

}
