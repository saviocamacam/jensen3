package jensen2;

public class Main {
	private static int numeroItens = 3;
	private static int numeroTransacoes = 4;
	private static int numeroAcessos = 9;
	private static GerenciadorTransacao gerenciador;

	public static void main(String[] args) {
		int ultimoIndice = 0;
		try {
			while (true) {
				ultimoIndice = TransacaoDao.pegarUltimoIndice();
				gerenciador = new GerenciadorTransacao(numeroItens, numeroTransacoes, numeroAcessos, ultimoIndice);
				TransacaoDao.gravarTransacoes(gerenciador);
				
				
				System.out.println( "ok" );
				Thread.sleep( 5 * 1000 );
				System.out.println( "ok" );  
				Thread.sleep( 10 * 1000 );
				System.out.println( "ok" );
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
 		}
	}

}
