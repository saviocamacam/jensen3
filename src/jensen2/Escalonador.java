/*Classe responsável pelo escalonamento*/
package jensen2;

import java.util.LinkedList;

public class Escalonador {
	private Schedule scheduleEscalonado;
	private LinkedList<Operacao> operacoesEscalonadas;
	private LinkedList<Operacao> operacoesEspera;
	private LinkedList<Dado> conjuntoDados;
	private LinkedList<Transacao> conjuntoTransacoes;
	private int flagEscalonador = -1; //0: schedule não escalonado / 1: schedule escalonado
	private int sizeOperacoesEspera = -1; //variável de contagem dos objetos em espera para verificação a cada nova atribuição com o término da lista de operações
	
	public Escalonador() {
		conjuntoDados = new LinkedList<>();
		scheduleEscalonado = new Schedule();
		conjuntoTransacoes = new LinkedList<>();
		operacoesEscalonadas = new LinkedList<>();
		operacoesEspera = new LinkedList<>();
		new LinkedList<>();
	}
	/*O funcionamento do escalonador trata do consumo dos itens da lista de operações capturada no arquivo de schedule e cada item (operacao) tem
	 * uma função a ser executada:
	 * Tipo S indica que uma transacao foi criada, assim são criados todos os objetos de transacao que precisam ser gerenciados.
	 * Tipo R ou W aqueles que precisam ser ordenados de acordo com a politica de escalonamento
	 * Tipo E indica que uma transacao deve ser submetida ao Commit e com ele começa a busca pelas esperas em todos os itens de dados
	 * coletados com o consumo desses itens na lista.
	 * Enquanto a lista de operações não se esgota com o consumo dos itens, indica que há itens a serem escalonados. Itens que conseguiram ser
	 * escalonados se tornam uma lista nova e os que não conseguiram se tornam outra e a cada vez que o s.scheduleInList se esgota
	 * a lista de operações em espera é atribuida a ele e o loop fica se repetindo até não sobrar ninguem ou se
	 * se repetir por duas vezes, o que indica o deadlock e o conjunto de transacoes que sobra são as que estão em espera.
	 * */
	public Schedule escalonar(Schedule s) {
		while(!s.getScheduleInList().isEmpty()) {
			Operacao o  = s.getScheduleInList().remove(); //consumo dos itens
				if(o.getAcesso() == Acesso.START) {
					conjuntoTransacoes.add(new Transacao(o.getIndice()));
				}
				else if(o.getAcesso() == Acesso.READ) {
					if(!conjuntoDados.contains(o.getDado())) {
						conjuntoDados.add(o.getDado());
						conjuntoDados.getLast().setEstado('S');
						conjuntoDados.getLast().addLockS(o.getIndice());
						conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).getConjuntoDados().add(o.getDado());
						operacoesEscalonadas.add(o);
					}
					else {
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndice(), 'R')) && o.getFlagLoopWait() == 1){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndice(), 'R'));
							o.setFlagLoopD();
						}
						if(getEstado(o.getDado()) == 'S' || getEstado(o.getDado()) == 'U') {
							if(!contemIndice(o.getDado(),o.getIndice()))
								conjuntoDados.get(getIndiceDado(o.getDado())).addLockS(o.getIndice());
							conjuntoDados.get(getIndiceDado(o.getDado())).setEstado('S');
							
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).getConjuntoDados().add(conjuntoDados.get(getIndiceDado(o.getDado())));
							}
							operacoesEscalonadas.add(o);
						}
						else {
							if(conjuntoDados.get(getIndiceDado(o.getDado())).getLockX() == o.getIndice()) {
								operacoesEscalonadas.add(o);
							}
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndice(), 'S');
								o.setFlagLoopE();
								operacoesEspera.add(o);
							}
						}
					}
				}
				else if(o.getAcesso() == Acesso.WRITE) {
					if(!conjuntoDados.contains(o.getDado())) {
						conjuntoDados.add(o.getDado());
						conjuntoDados.getLast().setEstado('X');
						conjuntoDados.getLast().addLockX(o.getIndice());
						conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).getConjuntoDados().add(o.getDado());
						operacoesEscalonadas.add(o);
					}
					else {
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndice(), 'W')) && o.getFlagLoopWait() == 1){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndice(), 'W'));
							o.setFlagLoopD();
						}
						if(isUnlock(o.getDado()) && listaEsperaVazia(o.getDado())) {
							conjuntoDados.get(getIndiceDado(o.getDado())).setEstado('X');
							conjuntoDados.get(getIndiceDado(o.getDado())).setLockX(o.getIndice());
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndice())).getConjuntoDados().add(o.getDado());
							}
							operacoesEscalonadas.add(o);
						}
						else if(isUnlock(o.getDado()) && !listaEsperaVazia(o.getDado())) {
						}
						else if(getEstado(o.getDado()) == 'S') {
							if(onlyMeLockS(o.getDado(), o.getIndice())) {
								upgradeLock(conjuntoDados.get(getIndiceDado(o.getDado())), o.getIndice());
								operacoesEscalonadas.add(o);
							}
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndice(), 'W');
								o.setFlagLoopE();
								operacoesEspera.add(o);
							}
						}
						else if (getEstado(o.getDado()) == 'X') {
							if(conjuntoDados.get(getIndiceDado(o.getDado())).getLockX() == o.getIndice())
								operacoesEscalonadas.add(o);
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndice(), 'W');
								o.setFlagLoopE();
								operacoesEspera.add(o);
							}
						}
						else {
							conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndice(), 'W');
							o.setFlagLoopE();
							operacoesEspera.add(o);
						}
					}
				}
				else if(o.getAcesso() == Acesso.END) {
					if(!isWait(o.getIndice())) {
						unlockData(o.getIndice());
						conjuntoTransacoes.remove(getIndiceTransacao(o.getIndice()));
						operacoesEscalonadas.add(new Operacao(Acesso.COMMIT, o.getIndice()));
					}
					else {
						o.setFlagLoopE();
						operacoesEspera.add(o);
					}
				}
			if(s.getScheduleInList().size() == 0) {
				if(!waitOperationsSizeEqual(operacoesEspera)) {
					s.setScheduleInList(operacoesEspera);
					operacoesEspera = new LinkedList<>();
				}
				else {
					flagEscalonador = 0;
					printDeadLock();
					scheduleEscalonado.setScheduleInList(operacoesEspera);
					return scheduleEscalonado;
				}
				
			}
		}
		flagEscalonador = 1;
		scheduleEscalonado.setScheduleInList(operacoesEscalonadas);
		return scheduleEscalonado;
	}
	
	private void printDeadLock() {
		System.out.print("DEADLOCK ENCONTRADO – TRANSAÇOES ENVOLVIDAS ");
		for(Transacao t : conjuntoTransacoes) {
			System.out.print("T" + t.getIndexNovo() + ", ");
		}
		System.out.println();
	}

	public int getFlagEscalonador() {
		return flagEscalonador;
	}
	//Verifica se a quantidade de operações em espera é igual a anterior: true indica deadlock ; false: indica fluxo normal
	private boolean waitOperationsSizeEqual(LinkedList<Operacao> operacoesEspera2) {
		if(sizeOperacoesEspera == operacoesEspera2.size()) {
			return true;
		}
		else {
			sizeOperacoesEspera = operacoesEspera2.size();
			return false;
		}
	}
	//método de retirada do indice que está sendo comitado das listas e variaveis de bloqueio dos dados
	private boolean unlockData(int index) {
		while(!conjuntoTransacoes.get(getIndiceTransacao(index)).getConjuntoDados().isEmpty()) {
			Dado dado = conjuntoTransacoes.get(getIndiceTransacao(index)).getConjuntoDados().remove();
			if(meuBloqueioExclusivo(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).setLockX(-1);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('U');
			}
			else if(onlyMeLockS(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).getLockS().remove((Integer)index);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('U');
			}
			else if(!onlyMeLockS(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).getLockS().remove((Integer)index);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('S');
			}
		}
		return true;
	}
	//verifica se bloqueio esclusivo de um dado corresponde ao indice de uma mesma transacao
	private boolean meuBloqueioExclusivo(Dado dado, int index) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockX() == index)
			return true;
		else return false;
	}
	//retorna true ou false para lista wait
	private boolean listaEsperaVazia(Dado dado) {
		if(conjuntoDados.get(getIndiceDado(dado)).getListaWait().isEmpty()) {
			return true;
		}
		else return false;
	}
	//verifica se um indice está na lista de compartilhamento de um dado
	public boolean contemIndice(Dado dado, int indice) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockS().contains(indice)){
			return true;
		}
		else return false;
	}
	//retorna o estado de um dado
	public char getEstado(Dado dado) {
		return conjuntoDados.get(getIndiceDado(dado)).getEstado();
	}
	//eleva o bloqueio de um dado de compartilhado para exclusivo
	private void upgradeLock(Dado dado, int indice) {
		dado.getLockS().remove();
		dado.setLockX(indice);
		dado.setEstado('X');
	}
	//verifica se o unico indice que tem o bloqueio compartilhado é igual ao indice testado
	private boolean onlyMeLockS(Dado dado, int indice) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockS().size() == 1 && conjuntoDados.get(getIndiceDado(dado)).getLockS().getLast() == indice) {
			return true;
		}
		return false;
	}
	//verifica se um indice está na lista de espera de algum item de dado
	private boolean isWait(int indice) {
		for(Dado d : conjuntoDados) {
			if(d.getListaWait().contains(new Wait(indice))) {
				return true;
			}
		}
		return false;
	}
	//verifica se um dado está desbloqueado
	private boolean isUnlock(Dado dado) {
		if(conjuntoDados.get(getIndiceDado(dado)).getEstado() == 'U') {
			return true;
		}
		else return false;
	}
	//retorna a posicao real, numa lista de transacoes, de uma transacao - não é o indice lógico da transacao
	private int getIndiceTransacao(int indiceObjeto) {
		return conjuntoTransacoes.indexOf(new Transacao(indiceObjeto));
	}
	//retorna a posicao real, numa lista de dados, de um dado
	private int getIndiceDado(Dado dado) {
		return conjuntoDados.indexOf(dado);
	}
	
	
	

}
