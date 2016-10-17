package jensen2;

import java.util.LinkedList;

public class Dado {
	private String nome;
	private LinkedList<Integer> lockS; //lista de indices que compartilham esse dado
	private int lockX; //indice da transacao que obtem o bloqueio exclusivo
	private LinkedList<Wait> listaWait; //Lista de itens em espera pelo dado
	private char estado; //Atributo usado no escalonamento
	
	public Dado(String nomeDado) {
		this.nome = nomeDado;
		this.lockS = new LinkedList<>();
		this.lockX = -1;
		this.listaWait = new LinkedList<>();
		this.estado = 'U';
	}

	public String getNome() {
		return nome;
	}
	public void enqueueS(Dado dado, Integer indice) {
		dado.lockS.add(indice);
	}
	public boolean isLockS(Dado dado) {
		if(!dado.lockS.isEmpty())
			return true;
		else return false;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public LinkedList<Wait> getListaWait() {
		return listaWait;
	}
	
	public LinkedList<Integer> getLockS() {
		return lockS;
	}
	
	public void setLockX(Integer indice) {
		this.lockX = indice;
	}
	
	public int getLockX() {
		return lockX;
	}

	public void setListaWait(LinkedList<Wait> listaWait) {
		this.listaWait = listaWait;
	}
	
	public char getEstado() {
		return estado;
	}
	//altera o estado do dado
	public void setEstado(char estado) {
		this.estado = estado;
	}
	//adiciona no bloqueio compartilhado
	public void addLockS(Integer indice) {
		lockS.add(indice); 
	}
	//remove do bloqueio compartilhado
	public void removeLockS(Integer indice) {
		lockS.remove(indice);
	}
	//adiciona transacao com bloqueio exclusivo
	public void addLockX(Integer indice) {
		lockX = indice; 
	}
	//adiciona transacoes na lista de espera
	public void addListaWait(Integer indice, char bloqueio) {
		listaWait.add(new Wait(indice, bloqueio));
	}
	
	@Override
	public boolean equals(Object o) {
		return this.nome.equals(((Dado) o).getNome());
	}
}