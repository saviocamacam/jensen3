/*Objeto composto dos campos necessários para salvamento das operações em espera com o índice e o tipo de bloqueio*/
package jensen2;

public class Wait {
	private Integer indice;
	private char bloqueio;
	public Wait(Integer indice, char bloqueio) {
		this.indice = indice;
		this.bloqueio = bloqueio;
	}
	public Wait(Integer indice) {
		this.indice = indice;
	}
	public char getBloqueio(){
		return bloqueio;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.indice == ((Wait) o).indice;
	}

}
