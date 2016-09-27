package jensen2;

public class Main {
	private static int numeroItens = 3;
	private static int numeroTransacoes = 4;
	private static int numeroAcessos = 9;
	private static GerenciadorTransacao gerenciador;

	public static void main(String[] args) {
		int ultimoIndice = 0;
		System.out.println( "Criando transacoes e gravando no banco..." );
		try {
			do {
				ultimoIndice = TransacaoDao.pegarUltimoIndice();
				gerenciador = new GerenciadorTransacao(numeroItens, numeroTransacoes, numeroAcessos, ultimoIndice);
				Schedule schedule = new Schedule(gerenciador.getListaTransacoes());
				TransacaoDao.gravarTransacoes(schedule);
				//System.out.println( "ok" );
				Thread.sleep( 10 * 1000 );
				//System.out.println( "ok" );
			} while(true);
		}catch (InterruptedException e) {
			e.printStackTrace();
 		}
	}

}
