package jensen2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransacaoDao {
	private static MinhaConexao minhaConexao;
	
	public TransacaoDao() {
		minhaConexao = new MinhaConexao();
	}

	public static void gravarTransacoes(GerenciadorTransacao gerenciador) {
		Transacao transacao = null;
		Operacao operacao = null;
		
		Connection conn = minhaConexao.getConnection();
		String sql = "";
		
		while(!gerenciador.getListaTransacoes().isEmpty()) {
			transacao = gerenciador.getListaTransacoes().removeFirst();
			
			while(!transacao.getFilaOperacoes().isEmpty()) {
				operacao = transacao.getFilaOperacoes().removeFirst();
				
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					
					
				} catch (SQLException e) {
					System.out.println("Erro na insercao da transacao");
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public static int pegarUltimoIndice() {
		
		
		
		return 0;
	}

}
