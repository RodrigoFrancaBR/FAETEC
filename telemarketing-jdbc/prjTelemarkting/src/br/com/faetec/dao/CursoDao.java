package br.com.faetec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faetec.model.Curso;

public abstract class CursoDao {

	private static Connection conexao;
	private static PreparedStatement stm;
	private static ResultSet rs;
	private static int linhas;

	private static void getConnection() {
		try {
			if ((conexao == null) || (conexao.isClosed())) {
				conexao = ConnectionFactory.getConnection();
				System.out.println("conexao aberta pelo CursoDao");
			}
		} catch (SQLException e) {
			System.out.println("erro ao tentar abrir a conexao com o CursoDao");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void save(Curso curso) {
		System.out.println("chamando o método save(Curso curso)");
		getConnection();
		try {
			stm = conexao
					.prepareStatement("INSERT INTO TB_CURSO (NOME_CURSO, DESCRICAO, ATIVO) VALUES (?,?,?)");
			stm.setString(1, curso.getNome());
			stm.setString(2, curso.getDescricao());
			stm.setBoolean(3, true);

			int linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo save(Curso curso)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}

	public static void update(Curso curso) {
		System.out.println("chamando o método update(Curso curso)");
		getConnection();
		try {

			stm = conexao
					.prepareStatement("UPDATE TB_CURSO SET NOME_CURSO=?, DESCRICAO=? WHERE ID_CURSO=?");

			stm.setString(1, curso.getNome());
			stm.setString(2, curso.getDescricao());
			stm.setInt(3, curso.getId());

			linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo update(Curso curso)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}

	public static List<Curso> findAll() {
		System.out.println("chamando no DAO o método findAllAtivos()");
		getConnection();
		List<Curso> cursos = new ArrayList<Curso>();
		Curso curso;
		String sql = "SELECT * FROM TB_CURSO WHERE ATIVO =?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, true);
			rs = stm.executeQuery();
			while (rs.next()) {
				curso = new Curso();

				curso.setId(rs.getInt("id_curso"));
				curso.setNome(rs.getString("nome_curso"));
				curso.setDescricao(rs.getString("descricao"));

				cursos.add(curso);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu algum erro no metodo findAllAtivos()");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return cursos;
	}

	public static Curso findById(int id) {
		System.out.println("findById(int id)");
		getConnection();
		Curso curso = null;
		String sql = "SELECT * FROM TB_CURSO WHERE ID_CURSO = ? AND ATIVO =?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id);
			stm.setBoolean(2, true);
			rs = stm.executeQuery();
			if (rs.next()) {
				curso = new Curso();

				curso.setId(rs.getInt("id_curso"));
				curso.setNome(rs.getString("nome_curso"));
				curso.setDescricao(rs.getString("descricao"));
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu algum erro no metodo findById()");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return curso;
	}

	public static Curso findByName(String nome) {
		System.out.println("findByName(String nome)");
		getConnection();
		Curso curso = null;
		String sql = "SELECT * FROM TB_CURSO WHERE NOME_CURSO = ? AND ATIVO =?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setString(1, nome);
			stm.setBoolean(2, true);
			rs = stm.executeQuery();
			if (rs.next()) {
				curso = new Curso();

				curso.setId(rs.getInt("id_curso"));
				curso.setNome(rs.getString("nome_curso"));
				curso.setDescricao(rs.getString("descricao"));
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu algum erro no metodo findById()");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return curso;
	}

	public static void deleteById(int id) {
		System.out.println("chamando o método deleteById(int id)");
		getConnection();
		String sql = "UPDATE TB_CURSO SET ATIVO = ? WHERE ID_CURSO=?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, false);
			stm.setInt(2, id);
			linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");

		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo deleteById(int id)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}

}
