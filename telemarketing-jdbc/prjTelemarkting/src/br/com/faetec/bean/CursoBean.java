package br.com.faetec.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.faetec.dao.CursoDao;
import br.com.faetec.model.Curso;

@SessionScoped
@ManagedBean
public class CursoBean {

	private List<Curso> cursos;
	private Curso curso = new Curso();

	public String buscarCursos() {
		cursos = CursoDao.findAll();
		curso = new Curso();
		return "cursos?faces-redirect=true";
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void editar(Curso curso) {
		this.curso = curso;
	}

	public void salvar() {
		if (null != curso.getId()) {
			CursoDao.update(curso);
		} else {
			CursoDao.save(curso);
		}
		curso = new Curso();
		buscarCursos();
	}

	public void limpar() {
		curso = new Curso();
	}
	
	public void excluir(Curso curso){
		CursoDao.deleteById(curso.getId());
		buscarCursos();
	}
	
	

}
