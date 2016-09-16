package jensen2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoDao {
	private static MinhaConexao minhaConexao;
	
	public TransacaoDao() {
		minhaConexao = new MinhaConexao();
	}

	public static void gravarTransacoes(Schedule schedule) {
		Operacao operacao = null;
		
		Connection conn = minhaConexao.getConnection();
		String sql = "INSERT INTO schedule(indiceTransacao, operacao, itemDado) VALUES (?, ?, ?)";
		PreparedStatement stmt = null;
		while(!schedule.getScheduleInList().isEmpty()) {
			operacao = schedule.getScheduleInList().removeFirst();
			try {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, operacao.getIndice());
				stmt.setString(2, operacao.getAcesso().texto);
				stmt.setString(3, operacao.getDado().getNome());
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Erro na insercao da transacao");
				e.printStackTrace();
			}
		}
		try {
			minhaConexao.releaseAll(stmt, conn);
		} catch (SQLException e) {
			System.out.println("Erro ao encerrar conex�o");
			e.printStackTrace();
		}
	}
	
	public static int pegarUltimoIndice() {
		int ultimoIndice = 0;
		minhaConexao = new MinhaConexao();
		Connection conn = minhaConexao.getConnection();
		String sql = "SELECT MAX(indiceTransacao) FROM schedule;";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			ultimoIndice = rs.getInt(1);
			
		} catch (SQLException e) {
			System.err.println("Erro na consulta ao �ltimo �ndice");
			e.printStackTrace();
		}
		return ultimoIndice;
	}

}
