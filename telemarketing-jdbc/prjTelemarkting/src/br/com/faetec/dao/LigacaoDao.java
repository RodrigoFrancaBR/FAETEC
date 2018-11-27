package br.com.faetec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.faetec.model.Curso;
import br.com.faetec.model.Ligacao;
import br.com.faetec.model.Pessoa;
import br.com.faetec.model.Turno;

public class LigacaoDao {
	private static Connection conexao;
	private static PreparedStatement stm;
	private static ResultSet rs;
	private static int linhas;

	private static void getConnection() {
		try {
			if ((conexao == null) || (conexao.isClosed())) {
				conexao = ConnectionFactory.getConnection();
				System.out.println("conexao aberta pelo ligacaoDao");
			}
		} catch (SQLException e) {
			System.out
					.println("erro ao tentar abrir a conexao com o ligacaoDao");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	// Todas as ligações feitas para uma determinada pessoa, seus cursos
	// ofertados e seus respectivos turnos escolhidos.

	public static List<Ligacao> findAllPhoneCallsBy(Pessoa pessoa) {
		System.out
				.println("chamando no DAO o método findAllPhoneCallsByPersonId(int ligacaoId)");
		getConnection();

		List<Ligacao> ligacoes = new ArrayList<Ligacao>();
		Ligacao ligacao;
		// Pessoa pessoa = new Pessoa();
		List<Curso> cursos;
		Curso curso;
		List<String> turnos;

		String sql = "SELECT L.*, C.*, P.*, LOC.* FROM TB_LIGACAO L, TB_CURSO C, TB_PESSOA P, TB_LIGACAO_OFERECE_CURSO LOC WHERE LOC.LIGACAO_ID = L.ID_LIGACAO AND LOC.CURSO_ID = C.ID_CURSO AND P.ID_PESSOA = L.PESSOA_ID AND L.PESSOA_ID = ? AND L.ATIVO = ?;";

		try {
			stm = conexao.prepareStatement(sql);

			stm.setInt(1, pessoa.getId());
			stm.setBoolean(2, true);
			rs = stm.executeQuery();

			while (rs.next()) {

				ligacao = new Ligacao();
				ligacao.setId(rs.getInt("id_ligacao"));

				// Se na lista de ligações existir essa ligação
				if (ligacoes.contains(ligacao)) {

					// Recupera a posição da ligação na lista
					int posicao = ligacoes.indexOf(ligacao);

					// Não precisa configurar a Ligação pois não muda.
					// Não precisa configurar a Pessoa pois não muda.

					/*
					 * Configurar um Curso, pois o curso pode ter sido outro,
					 * ofertado na mesma ligacao.
					 */

					curso = new Curso();
					curso.setId(rs.getInt("id_curso"));
					curso.setNome(rs.getString("nome_curso"));
					curso.setDescricao(rs.getString("descricao"));

					// Configurar um Turno
					turnos = new ArrayList<String>();

					if (rs.getInt("manha") != 0) {

						turnos.add(Turno.M.getDescricao());
					}
					if (rs.getInt("tarde") != 0) {

						turnos.add(Turno.T.getDescricao());
					}
					if (rs.getInt("noite") != 0) {

						turnos.add(Turno.N.getDescricao());
					}

					// add turno ao curso
					curso.setTurnos(turnos);

					/*
					 * Recuperando a ligação existente e adicionando o novo
					 * curso a lista de cursos existente, nesta ligação.
					 */
					ligacoes.get(posicao).getCursos().add(curso);

					// Se a ligação não existir na lista de ligações
				} else {

					// Configurando uma Ligação
					ligacao.setInicio(rs.getTime("inicio"));
					ligacao.setFim(rs.getTime("fim"));

					java.sql.Date dataSql = rs.getDate("data_ligacao");
					Calendar dataCalendar = Calendar.getInstance();
					dataCalendar.setTime(new java.util.Date(dataSql.getTime()));

					ligacao.setDataLigacao(dataCalendar);
					ligacao.setObservacoes(rs.getString("observacoes"));

					// Configurar uma Pessoa

					pessoa.setId(rs.getInt("id_pessoa"));
					pessoa.setNome(rs.getString("nome_pessoa"));
					pessoa.setSobreNome(rs.getString("sobre_nome"));
					pessoa.setCpf(rs.getString("cpf"));

					pessoa.setEmail(rs.getString("email"));
					pessoa.setPai(rs.getString("pai"));
					pessoa.setMae(rs.getString("mae"));
					pessoa.setCep(rs.getString("cep"));
					pessoa.setEndereco(rs.getString("endereco"));
					pessoa.setBairro(rs.getString("bairro"));

					pessoa.setCelular(rs.getString("celular"));
					pessoa.setResidencial(rs.getString("residencial"));

					pessoa.setCelularPai(rs.getString("cel_pai"));
					pessoa.setCelularMae(rs.getString("cel_mae"));

					// Configurar um Curso
					curso = new Curso();
					curso.setId(rs.getInt("id_curso"));
					curso.setNome(rs.getString("nome_curso"));
					curso.setDescricao(rs.getString("descricao"));

					// Configurar um Turno
					turnos = new ArrayList<String>();

					if (rs.getInt("manha") != 0) {

						turnos.add(Turno.M.getDescricao());
					}
					if (rs.getInt("tarde") != 0) {

						turnos.add(Turno.T.getDescricao());
					}
					if (rs.getInt("noite") != 0) {

						turnos.add(Turno.N.getDescricao());
					}

					// add turno ao curso
					curso.setTurnos(turnos);

					// Garantir que a lista de cursos está vazia
					ligacao.setCursos(new ArrayList<Curso>());

					// Adicionar um curso a lista de cursos
					ligacao.getCursos().add(curso);

					// Adicionando uma pessoa a essa ligacao
					ligacao.setPessoa(pessoa);

					// Adicionar uma ligaçã a lista de ligações
					ligacoes.add(ligacao);

				}
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo DAO findAllPhoneCallsByPersonId(int ligacaoId)");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return ligacoes;
	}

	public static void save(Ligacao ligacao) {
		System.out.println("chamando no DAO o método save(Ligacao ligacao)");
		getConnection();
		String sql = "INSERT INTO TB_LIGACAO(INICIO, FIM, DATA_LIGACAO, OBSERVACOES, PESSOA_ID, ATIVO)"
				+ " VALUES(?,?,?,?,?,?)";

		try {
			stm = conexao.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setTime(1, ligacao.getInicio());
			stm.setTime(2, ligacao.getFim());
			stm.setDate(3, new java.sql.Date(ligacao.getDataLigacao()
					.getTimeInMillis()));
			stm.setString(4, ligacao.getObservacoes());
			stm.setInt(5, ligacao.getPessoa().getId());
			stm.setBoolean(6, true);

			linhas = stm.executeUpdate();

			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
			if (linhas == 1) {
				ResultSet generatedKeys = stm.getGeneratedKeys();
				if (generatedKeys.next()) {
					ligacao.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException(
							"Erro ao tentar obter o id da ligação.");
				}
			} else {
				throw new SQLException("Nao foi possivel incluir 1 registro.");
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no método do DAO save(Ligacao ligacao)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}

	public static void savePhoneCallWithCourse(Ligacao ligacao) {

		String sql = "INSERT INTO TB_LIGACAO_OFERECE_CURSO(LIGACAO_ID, CURSO_ID, MANHA, TARDE, NOITE)"
				+ " VALUES(?,?,?,?,?);";
		for (Curso c : ligacao.getCursos()) {

			try {
				getConnection();

				stm = conexao.prepareStatement(sql);

				stm.setInt(1, ligacao.getId());
				stm.setInt(2, c.getId());
				stm.setBoolean(3, c.getTurnos().toString().contains("manha"));
				stm.setBoolean(4, c.getTurnos().toString().contains("tarde"));
				stm.setBoolean(5, c.getTurnos().toString().contains("noite"));

				linhas = stm.executeUpdate();

				System.out.println("Foram modificadas, " + linhas
						+ " com sucesso!");
			} catch (SQLException e) {
				System.out
						.println("Ocorreu algum erro no método do DAO savePhoneCallWithCourse(Ligacao ligacao)");
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				conexao = null;
				ConnectionFactory.closeAll(conexao, stm, rs);
			}
		}
	}

	public static void deleteById(int id) {
		System.out.println("chamando no DAO o método deleteById(int id)");
		getConnection();
		String sql = "UPDATE TB_LIGACAO SET ATIVO = ? WHERE ID_LIGACAO = ?;";

		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, false);
			stm.setInt(2, id);
			linhas = stm.executeUpdate();

			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no método do DAO deleteById(int id)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}

	}

	public static List<Ligacao> findAll() {
		System.out.println("chamando no DAO o método findAllAtivos()");
		getConnection();
		List<Ligacao> ligacoes = new ArrayList<Ligacao>();
		Ligacao ligacao;
		Pessoa pessoa;

		String sql = "SELECT L.ID_LIGACAO, L.DATA_LIGACAO, L.INICIO, L.FIM, L.OBSERVACOES, "
				+ "P.ID_PESSOA, P.NOME_PESSOA, P.SOBRE_NOME, P.CPF, P.EMAIL, P.PAI, P.MAE, "
				+ "P.CEP, P.ENDERECO, P.BAIRRO, P.CELULAR, P.RESIDENCIAL, P.CEL_PAI, P.CEL_MAE "
				+ "FROM TB_LIGACAO L, TB_PESSOA P "
				+ "WHERE L.PESSOA_ID = P.ID_PESSOA "
				+ "AND L.ATIVO = ? "
				+ "ORDER BY L.DATA_LIGACAO;";

		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, true);

			rs = stm.executeQuery();
			while (rs.next()) {

				ligacao = new Ligacao();

				ligacao.setId(rs.getInt("id_ligacao"));
				ligacao.setInicio(rs.getTime("inicio"));
				ligacao.setFim(rs.getTime("fim"));

				java.sql.Date dataSql = rs.getDate("data_ligacao");
				Calendar dataCalendar = Calendar.getInstance();

				dataCalendar.setTime(new java.util.Date(dataSql.getTime()));

				ligacao.setDataLigacao(dataCalendar);
				ligacao.setObservacoes(rs.getString("observacoes"));

				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_pessoa"));
				pessoa.setSobreNome(rs.getString("sobre_nome"));
				pessoa.setCpf(rs.getString("cpf"));

				pessoa.setEmail(rs.getString("email"));
				pessoa.setPai(rs.getString("pai"));
				pessoa.setMae(rs.getString("mae"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setEndereco(rs.getString("endereco"));
				pessoa.setBairro(rs.getString("bairro"));

				pessoa.setCelular(rs.getString("celular"));
				pessoa.setResidencial(rs.getString("residencial"));

				pessoa.setCelularPai(rs.getString("cel_pai"));
				pessoa.setCelularMae(rs.getString("cel_mae"));

				ligacao.setPessoa(pessoa);

				ligacoes.add(ligacao);
			}

		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo DAO findAllAtivos()");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return ligacoes;
	}

	public static Ligacao findPhoneCallBy(Ligacao ligacao) {
		System.out
				.println("chamando no DAO o método findPhoneCallBy(Integer idLigacao)");
		// Ligacao ligacaoNova = new Ligacao();
		Pessoa pessoa = new Pessoa();
		Curso curso;
		List<Curso> cursos = new ArrayList<Curso>();
		List<String> turnos;
		String sql = "SELECT L.*, LOC.*, C.*, P.* "
				+ "FROM TB_LIGACAO L, TB_LIGACAO_OFERECE_CURSO LOC, TB_CURSO C, TB_PESSOA P "
				+ "WHERE LOC.LIGACAO_ID = L.ID_LIGACAO AND "
				+ "LOC.CURSO_ID = C.ID_CURSO AND "
				+ "P.ID_PESSOA = L.PESSOA_ID AND " + "L.ID_LIGACAO = ? AND "
				+ "L.ATIVO = ?;";

		getConnection();

		try {
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, ligacao.getId());
			stm.setBoolean(2, true);

			rs = stm.executeQuery();
			while (rs.next()) {

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_pessoa"));
				pessoa.setSobreNome(rs.getString("sobre_nome"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setPai(rs.getString("pai"));
				pessoa.setMae(rs.getString("mae"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setEndereco(rs.getString("endereco"));
				pessoa.setBairro(rs.getString("bairro"));

				pessoa.setCelular(rs.getString("celular"));
				pessoa.setResidencial(rs.getString("residencial"));
				pessoa.setCelularPai(rs.getString("cel_pai"));
				pessoa.setCelularMae(rs.getString("cel_mae"));

				curso = new Curso();

				curso.setNome(rs.getString("nome_curso"));
				curso.setDescricao(rs.getString("descricao"));

				turnos = new ArrayList<String>();

				if (rs.getInt("manha") != 0) {

					turnos.add(Turno.M.getDescricao());
				}
				if (rs.getInt("tarde") != 0) {

					turnos.add(Turno.T.getDescricao());
				}
				if (rs.getInt("noite") != 0) {

					turnos.add(Turno.N.getDescricao());
				}

				curso.setTurnos(turnos);

				cursos.add(curso);

				ligacao.setId(rs.getInt("id_ligacao"));
				ligacao.setInicio(rs.getTime("inicio"));
				ligacao.setFim(rs.getTime("fim"));

				java.sql.Date dataSql = rs.getDate("data_ligacao");
				Calendar dataCalendar = Calendar.getInstance();

				dataCalendar.setTime(new java.util.Date(dataSql.getTime()));

				ligacao.setDataLigacao(dataCalendar);
				ligacao.setObservacoes(rs.getString("observacoes"));

			}

			ligacao.setPessoa(pessoa);
			ligacao.setCursos(cursos);

		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo DAO findPhoneCall(Ligacao ligacao)");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return ligacao;
	}	

	public static List<Pessoa> findAllPeopleByFone(String telefone) {

		String sql = "SELECT * FROM TB_PESSOA"
				+ " WHERE ( CELULAR = ? OR RESIDENCIAL = ? OR CEL_PAI = ? OR CEL_MAE = ?) AND ATIVO = ?;";
		getConnection();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Pessoa pessoa;
		try {
			stm = conexao.prepareStatement(sql);
			stm.setString(1, telefone);
			stm.setString(2, telefone);
			stm.setString(3, telefone);
			stm.setString(4, telefone);
			stm.setBoolean(5, true);
			rs = stm.executeQuery();
			while (rs.next()) {
				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_pessoa"));
				pessoa.setSobreNome(rs.getString("sobre_nome"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setPai(rs.getString("pai"));
				pessoa.setMae(rs.getString("mae"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setEndereco(rs.getString("endereco"));
				pessoa.setBairro(rs.getString("bairro"));

				pessoa.setCelular(rs.getString("celular"));
				pessoa.setResidencial(rs.getString("residencial"));
				pessoa.setCelularPai(rs.getString("cel_pai"));
				pessoa.setCelularMae(rs.getString("cel_mae"));

				pessoas.add(pessoa);
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo findAllPeopleByFone(String telefone)");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return pessoas;
	}

	public static List<Pessoa> findAllPeopleByName(String nome) {
		System.out
				.println("chamando no DAO o método findAllPeopleByName(String nome)");
		String sql = "SELECT * FROM TB_PESSOA"
				+ " WHERE NOME_PESSOA = ? AND ATIVO = ?;";
		getConnection();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Pessoa pessoa;
		try {
			stm = conexao.prepareStatement(sql);
			stm.setString(1, nome);
			stm.setBoolean(2, true);
			rs = stm.executeQuery();
			while (rs.next()) {
				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_pessoa"));
				pessoa.setSobreNome(rs.getString("sobre_nome"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setPai(rs.getString("pai"));
				pessoa.setMae(rs.getString("mae"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setEndereco(rs.getString("endereco"));
				pessoa.setBairro(rs.getString("bairro"));

				pessoa.setCelular(rs.getString("celular"));
				pessoa.setResidencial(rs.getString("residencial"));
				pessoa.setCelularPai(rs.getString("cel_pai"));
				pessoa.setCelularMae(rs.getString("cel_mae"));

				pessoas.add(pessoa);
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo findAllPeopleByFone(String telefone)");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return pessoas;
	}

}
