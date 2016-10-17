package jensen2;

public class Operacao {
	private int idOperacao;
	private Acesso acesso;
	private int indice;
	private int estado;
	private Dado dado;
	private int flagLoopWait;
	
	
	public Operacao(String nome, Acesso acesso, int meuIndice) {
		this.setAcesso(acesso);
		this.setIndice(meuIndice);
		this.setDado(new Dado(nome));
		this.setEstado(0);
	}
	
	public Operacao(int idoperacao, String nome, Acesso acesso, int meuIndice, int estado) {
		this.setIdOperacao(idoperacao);
		this.setAcesso(acesso);
		this.setIndice(meuIndice);
		this.setDado(new Dado(nome));
		this.setEstado(estado);
	}
	
	private void setEstado(int i) {
		this.estado = i;
	}
	
	private void setIdOperacao(int idoperacao2) {
		this.idOperacao = idoperacao2;
	}
	
	public int getIdOperacao() {
		return idOperacao;
	}

	public Operacao(Acesso acesso, int ultimoIndice) {
		this.setAcesso(acesso);
		this.setIndice(ultimoIndice);
		this.setDado(new Dado(null));
	}

	public Dado getDado() {
		return dado;
	}

	public void setDado(Dado dado) {
		this.dado = dado;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public int getEstado() {
		return this.estado;
	}
	
	public void setFlagLoopE() {
		this.flagLoopWait = 1;
	}
	
	public void setFlagLoopD() {
		this.flagLoopWait = 0;
	}
	
	public int getFlagLoopWait() {
		return flagLoopWait;
	}

}
