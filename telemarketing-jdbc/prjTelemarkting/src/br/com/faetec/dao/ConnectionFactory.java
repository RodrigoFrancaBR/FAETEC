package br.com.faetec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ConnectionFactory {
	private static final String URL = "jdbc:mysql://localhost:3306/db_telemarketing";
	private static final String PASSWORD = "root";
	private static final String USER = "root";

	public static Connection getConnection() {

		try {
			// // Faz com que a classe seja carregada pela JVM
			Class.forName("com.mysql.jdbc.Driver");
			// Cria a conexão com o banco de dados
			return DriverManager.getConnection(URL, USER, PASSWORD);

		} catch (Exception e) {
			System.out.println("Ocorreu um erro no getConnection" + e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void closeAll(Connection conexao, PreparedStatement stm,
			ResultSet rs) {
		try {

			// se rs for diferente de null e rs não estiver fechado
			if (rs != null && !rs.isClosed()) {
				rs.close();
				System.out.println("ResultSet Fechado");
			}
			if (stm != null && !stm.isClosed()) {
				stm.close();
				System.out.println("PreparedStatement Fechado");
			}
			if (conexao != null && !conexao.isClosed()) {
				conexao.close();
				//conexao = null;
				System.out.println("Connection Fechado");
				//System.out.println("Connection Null");
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro no closeAll" + e);
			e.printStackTrace();

		}
	}

}
