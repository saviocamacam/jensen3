package jensen2;

import java.util.Scanner;

public class Main {
	private static int numeroItens = 3;
	private static int numeroTransacoes = 4;
	private static int numeroAcessos = 9;
	private static Scanner scanner;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println( "Criando transacoes e gravando no banco..." );
		Produtor produtor = new Produtor(numeroItens, numeroTransacoes, numeroAcessos);
		produtor.start();
		System.out.println("Pressione Enter para encerrar a producao!");
		
		if(scanner.hasNextLine()) {
			System.out.println("Producao encerrada");
			produtor.setFlag(false);
		}
		
		Schedule scheduleIn = TransacaoDao.buscarTransacoes(0);
		Schedule scheduleOut = null;
		Escalonador escalonador = new Escalonador();
		
		do{
			scheduleOut = escalonador.escalonar(scheduleIn);
			TransacaoDao.gravarTransacoes(scheduleOut, "scheduleOut");
			scheduleIn = TransacaoDao.buscarTransacoes(scheduleIn.getScheduleInList().getLast().getIdOperacao());
		} while (true);
		
	}

}
