package br.com.faetec.bean;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.faetec.dao.CursoDao;
import br.com.faetec.dao.LigacaoDao;
import br.com.faetec.dao.PessoaDao;
import br.com.faetec.model.Curso;
import br.com.faetec.model.Ligacao;
import br.com.faetec.model.Pessoa;

@ManagedBean
@SessionScoped
public class LigacaoBean {

	private List<Ligacao> ligacoes = new ArrayList<Ligacao>();
	private Ligacao ligacao;
	private String telefone;
	private String nome;
	private List<Pessoa> pessoas;
	private Pessoa pessoa;
	private int qtdCursoSelecionado;
	private ArrayList<Curso> tabelaExibeCursosEscolhidos;
	private List<Curso> cursos;
	private String cursoSelecionado;
	private List<String> turnosSelecionados;
	private Curso curso;

	public String buscarLigacoes() {
		ligacoes = LigacaoDao.findAll();
		nome = "";
		telefone = "";
		pessoas = new ArrayList<Pessoa>();
		return "ligacoes?faces-redirect=true";
	}
	
	public void buscarLigacoesViewAction() {
		ligacoes = LigacaoDao.findAll();
		nome = "";
		telefone = "";
		pessoas = new ArrayList<Pessoa>();		
	}

	public List<Ligacao> getLigacoes() {
		return ligacoes;
	}

	public String buscarLigacaoPor(Ligacao ligacao) {
		// readOnly = true;
		this.ligacao = LigacaoDao.findPhoneCallBy(ligacao);
		return "detalhesDaLigacao?faces-redirect=true";
	}

	public void buscar() {

		if (nome.trim().equals("")) {
			pessoas = LigacaoDao.findAllPeopleByFone(telefone);
		} else {
			pessoas = LigacaoDao.findAllPeopleByName(nome);
		}
	}

	public void limpar() {
		nome = "";
		telefone = "";
		pessoas = new ArrayList<Pessoa>();
	}

	public String buscarLigacoesPor(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.pessoa.setLigacoes(LigacaoDao.findAllPhoneCallsBy(pessoa));
		return "exibirLigacoesDaPessoa?faces-redirect=true";
	}

	// usando o scopo pessoa
	public void salvarPessoa() {
		if (null != pessoa.getId()) {
			PessoaDao.update(pessoa);
		} else {
			PessoaDao.save(pessoa);
		}
	}
	
// usando o scopo ligacao.pessoa
	public void salvarLigacaoDaPessoa() {
		if (null != ligacao.getPessoa().getId()) {
			PessoaDao.update(ligacao.getPessoa());
		} else {
			PessoaDao.save(ligacao.getPessoa());
		}
	}
		

	public String ligar(Pessoa pessoa) {

		this.pessoa = pessoa;

		qtdCursoSelecionado = 1;

		tabelaExibeCursosEscolhidos = new ArrayList<Curso>();

		cursos = CursoDao.findAll();

		ligacao = new Ligacao();

		Calendar dataAtual = Calendar.getInstance();

		SimpleDateFormat sdfD = new SimpleDateFormat("dd/MM/yyyy");

		String dataApenas = sdfD.format(dataAtual.getTime());

		Calendar dataformatada = Calendar.getInstance();
		try {
			dataformatada.setTime(sdfD.parse(dataApenas));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ligacao.setDataLigacao(dataformatada);

		SimpleDateFormat sdfH = new SimpleDateFormat("HH:mm:ss");

		String horaApenas = sdfH.format(dataAtual.getTime());

		ligacao.setInicio(Time.valueOf(horaApenas));
		// pessoas = new ArrayList<Pessoa>();

		return "ligar?faces-redirect=true";
	}

	public void adicionarCurso() throws Exception {
		System.out.println("Chamando o método adicionarCurso()");

		if (!turnosSelecionados.isEmpty()) {
			if (qtdCursoSelecionado <= 3) {

				curso = CursoDao.findByName(cursoSelecionado);

				curso.setTurnos(turnosSelecionados);
				tabelaExibeCursosEscolhidos.add(curso);
				qtdCursoSelecionado++;
			} else {
				throw new Exception(
						"Você só pode adicionar 3 cursos,"
								+ " se precisar adicionar mais, deve excluir algum antes");
			}
		}
		cursos.remove(curso);
		turnosSelecionados = new ArrayList<String>();
	}

	public void desfazer() {
		tabelaExibeCursosEscolhidos = new ArrayList<Curso>();
		ligacao.setObservacoes("");
		qtdCursoSelecionado = 1;
	}

	public void excluirCurso(Curso curso) {
		tabelaExibeCursosEscolhidos.remove(curso);
		cursos.add(curso);
		qtdCursoSelecionado--;

	}

	public String salvarLigacao() throws Exception {		

		if (!getTabelaExibeCursosEscolhidos().isEmpty()
				&& !ligacao.getObservacoes().equals("")) {

			Calendar horaFinal = Calendar.getInstance();
			SimpleDateFormat sdfH = new SimpleDateFormat("HH:mm:ss");
			String fimLigacao = sdfH.format(horaFinal.getTime());

			ligacao.setFim(Time.valueOf(fimLigacao));

			ligacao.setCursos(getTabelaExibeCursosEscolhidos());

			ligacao.setPessoa(pessoa);

			LigacaoDao.save(ligacao);

			LigacaoDao.savePhoneCallWithCourse(ligacao);

			//ligacoes.add(ligacao);
			
			buscarLigacoes();

		} else {
			/*readOnly = true;*/
			throw new Exception(
					"Favor escolher ao menos um turno para o curso "
							+ this.cursoSelecionado
							+ " , e o campo Observação precisa ser preenchido");

		}		
		return "ligacoes?faces-redirect=true";
	}
	
	public void excluirLigacaoDasLigacoes(Ligacao ligacao){
		LigacaoDao.deleteById(ligacao.getId());
		ligacoes = LigacaoDao.findAll();
		/*pessoa.setLigacoes(LigacaoDao.findAllPhoneCallsBy(pessoa));*/	
	}
	
	
	public void excluirLigacaoDaPessoa(Ligacao ligacao){
		LigacaoDao.deleteById(ligacao.getId());
		pessoa.setLigacoes(LigacaoDao.findAllPhoneCallsBy(pessoa));	
	}

	/*
	 * public String menuPrincipal() { rendered = false; return
	 * "principal?faces-redirect=true"; }
	 */

	public Ligacao getLigacao() {
		return ligacao;
	}

	public void setLigacao(Ligacao ligacao) {
		this.ligacao = ligacao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public String getCursoSelecionado() {
		return cursoSelecionado;
	}

	public void setCursoSelecionado(String cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public List<String> getTurnosSelecionados() {
		return turnosSelecionados;
	}

	public void setTurnosSelecionados(List<String> turnosSelecionados) {
		this.turnosSelecionados = turnosSelecionados;
	}

	public ArrayList<Curso> getTabelaExibeCursosEscolhidos() {
		return tabelaExibeCursosEscolhidos;
	}

	public void setTabelaExibeCursosEscolhidos(
			ArrayList<Curso> tabelaExibeCursosEscolhidos) {
		this.tabelaExibeCursosEscolhidos = tabelaExibeCursosEscolhidos;
	}
	
	

}
