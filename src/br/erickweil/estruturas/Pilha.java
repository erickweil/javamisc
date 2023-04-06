package br.erickweil.estruturas;

import br.erickweil.utilidades.WeilArrays;

public class Pilha {
	
	public int[] array;
	public int topo;
	
	public Pilha(int capacide) {
		array = new int[capacide];
		topo = -1;
	}
	
	/**
	 * soma 1 à topo e o array recebe o valor na posição topo
	 * @param valor Valor que será inserido
	 */
	public void push(int valor) {
		topo++;
		array[topo] = valor;
	}
	
	/**
	 * guarda o último da pilha, que é indicado por 'topo' em uma variável,
	 * então subtrai 1 de topo, e retorna a variável temporária
	 */
	public int pop() {
		int temp = array[topo];
		array[topo] = 0; // ISSO NÃO É NECESSÁRIO!!
		topo--;
		return temp;
	}
	
	// size(): retorna topo + 1
	public int size() {
		return topo + 1;
	}
	
	// isEmpty(): retorna verdadeiro se topo é menor que 0
	public boolean isEmpty() {
		return topo < 0;
	}
	
	// top(): retorna o elemento na posição topo em array
	public int peek() {
		return array[topo];
	}
	
	public void printar() {
		for(int i =0;i<array.length;i++) {
			System.out.print(array[i]);
			if(topo == i) {
				System.out.println(" <-- topo");
			} else {
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Considera-se que há um Array chamado ‘array’ que é iniciado com um
		valor arbitrário de espaços chamado capacidade, que é o máximo de
		elementos que poderão ser inseridos, já que um Array não pode ser
		redimensionado após criado.
		
		E uma variável topo que inicia com o valor -1, que indica a posição do
		último elemento no array.*/
		
		Pilha minhaPilha = new Pilha(4);
		
		System.out.println("Valor de topo antes do push:"+minhaPilha.topo);
		minhaPilha.printar();
		
		minhaPilha.push(1);
		minhaPilha.push(2);
		minhaPilha.push(3);
		
		
		System.out.println("Valor de topo depois do push:"+minhaPilha.topo);
		minhaPilha.printar();
		
		int poppado;
		
		poppado = minhaPilha.pop();
		System.out.println("Fez o pop() --> "+poppado);
		
		poppado = minhaPilha.pop();
		System.out.println("Fez o pop() --> "+poppado);
		
		poppado = minhaPilha.pop();
		System.out.println("Fez o pop() --> "+poppado);
		
		System.out.println("Valor de topo depois dos pop's:"+minhaPilha.topo);
		minhaPilha.printar();
		
		
		/*
		*/
	}

}
