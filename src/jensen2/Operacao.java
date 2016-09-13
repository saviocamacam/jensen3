package jensen2;

public class Operacao {
	private Acesso acesso;
	private int indice;
	private Dado dado;
	
	
	public Operacao(String nome, Acesso acesso, int meuIndice) {
		this.acesso = acesso;
		this.indice = meuIndice;
		this.dado = new Dado(nome);
	}
	
	public Operacao(Acesso acesso, int ultimoIndice) {
		this.acesso = acesso;
		this.indice = ultimoIndice;
		this.dado = new Dado(null);
	}

}
