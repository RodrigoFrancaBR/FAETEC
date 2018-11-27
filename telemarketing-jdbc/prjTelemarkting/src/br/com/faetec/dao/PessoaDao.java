package br.com.faetec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faetec.model.Pessoa;

public abstract class PessoaDao {
	private static Connection conexao;
	private static PreparedStatement stm;
	private static ResultSet rs;
	private static int linhas;

	private static void getConnection() {
		try {
			if ((conexao == null) || (conexao.isClosed())) {
				conexao = ConnectionFactory.getConnection();
				System.out.println("conexao aberta pelo PessoaDao");
			}
		} catch (SQLException e) {
			System.out
					.println("erro ao tentar abrir a conexao com o PessoaDao");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void update(Pessoa pessoa) {
		System.out.println("chamando no DAO o método update(pessoa pessoa)");
		getConnection();
		String sql = "UPDATE TB_PESSOA SET NOME_PESSOA=?, SOBRE_NOME=?, CPF=?, EMAIL=?, PAI=?, MAE=?,"
				+ " CEP=?, ENDERECO=?, BAIRRO=?, CELULAR=?, RESIDENCIAL=?, CEL_PAI=?, CEL_MAE=? WHERE ID_PESSOA = ? AND ATIVO =?;";
		try {

			stm = conexao.prepareStatement(sql);

			stm.setString(1, pessoa.getNome());
			stm.setString(2, pessoa.getSobreNome());
			stm.setString(3, pessoa.getCpf());
			stm.setString(4, pessoa.getEmail());
			stm.setString(5, pessoa.getPai());
			stm.setString(6, pessoa.getMae());
			stm.setString(7, pessoa.getCep());
			stm.setString(8, pessoa.getEndereco());
			stm.setString(9, pessoa.getBairro());
			stm.setString(10, pessoa.getCelular());
			stm.setString(11, pessoa.getResidencial());
			stm.setString(12, pessoa.getCelularPai());
			stm.setString(13, pessoa.getCelularMae());
			stm.setInt(14, pessoa.getId());
			stm.setBoolean(15, true);

			linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo update(pessoa pessoa)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}

	public static void save(Pessoa pessoa) {
		System.out.println("chamando o método save(Pessoa pessoa)");
		getConnection();
		String sql = "INSERT INTO TB_PESSOA ( NOME_PESSOA, SOBRE_NOME, CPF, EMAIL, PAI, MAE, CEP, ENDERECO, BAIRRO,"
				+ " CELULAR, RESIDENCIAL, CEL_PAI, CEL_MAE, ATIVO) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			getConnection();
			stm = conexao.prepareStatement(sql);

			stm.setString(1, pessoa.getNome());
			stm.setString(2, pessoa.getSobreNome());
			stm.setString(3, pessoa.getCpf());
			stm.setString(4, pessoa.getEmail());
			stm.setString(5, pessoa.getPai());
			stm.setString(6, pessoa.getMae());
			stm.setString(7, pessoa.getCep());
			stm.setString(8, pessoa.getEndereco());
			stm.setString(9, pessoa.getBairro());

			stm.setString(10, pessoa.getCelular());
			stm.setString(11, pessoa.getResidencial());
			stm.setString(12, pessoa.getCelularPai());
			stm.setString(13, pessoa.getCelularMae());
			stm.setBoolean(14, true);

			int linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo save(Pessoa pessoa)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}
	
	

	public static List<Pessoa> findAllPeopleByFone(String telefone) {
		System.out
				.println("chamando no DAO o método findAllPessoasByFone(String telefone)");
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

	public static List<Pessoa> findAll() {
		System.out.println("chamando no DAO o método findAllAtivos()");
		getConnection();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Pessoa pessoa;
		String sql = "SELECT * FROM TB_PESSOA WHERE ATIVO = ?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, true);
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
			System.out.println("Ocorreu algum erro no metodo findAllAtivos()");
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return pessoas;
	}

	public static void deleteById(int id) {
		System.out.println("chamando o método deleteById(int id)");
		getConnection();
		String sql = "UPDATE TB_PESSOA SET ATIVO = ? WHERE ID_PESSOA = ?;";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setBoolean(1, false);
			stm.setInt(2, id);
			linhas = stm.executeUpdate();
			System.out
					.println("Foram modificadas, " + linhas + " com sucesso!");

		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo deleteById(int id");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
	}
	
	public static Pessoa findById(int id) {
		System.out.println("chamando o método findById(int id)");
		Pessoa pessoa = null;
		getConnection();
		String sql = "SELECT * FROM TB_PESSOA WHERE ID_PESSOA = ? AND ATIVO = ? ";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id);
			stm.setBoolean(2, true);
			rs = stm.executeQuery();

			if (rs.next()) {
				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_Pessoa"));
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
				pessoa.setAtivo(rs.getBoolean("ativo"));

				return pessoa;
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo findByName(String nome)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return pessoa;
	}

	public static Pessoa findByName(String nome) {
		System.out.println("chamando o método findByName(String nome)");
		Pessoa pessoa = null;
		getConnection();
		String sql = "SELECT * FROM TB_PESSOA WHERE NOME_PESSOA = ? AND ATIVO = ? ";
		try {
			stm = conexao.prepareStatement(sql);
			stm.setString(1, nome);
			stm.setBoolean(2, true);
			rs = stm.executeQuery();

			if (rs.next()) {
				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("id_pessoa"));
				pessoa.setNome(rs.getString("nome_Pessoa"));
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
				pessoa.setAtivo(rs.getBoolean("ativo"));

				return pessoa;
			}
		} catch (SQLException e) {
			System.out
					.println("Ocorreu algum erro no metodo findByName(String nome)");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			conexao = null;
			ConnectionFactory.closeAll(conexao, stm, rs);
		}
		return pessoa;
	}

}
