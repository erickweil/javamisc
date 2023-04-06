package br.erickweil.arvores;

public class Arvore {

	public int valor;
	public Arvore direita;
	public Arvore esquerda;
	
	public Arvore(int valor, Arvore esquerda, Arvore direita) {
		this.valor = valor;
		this.direita = direita;
		this.esquerda = esquerda;
	}
	
	// 2.1 Método atravessar
	public void atravessar() {
		if(esquerda != null) esquerda.atravessar();
		
		System.out.println(this.valor);
		
		if(direita != null) direita.atravessar();
	}
	
	public void adicionarElemento(int valor) {
		if(valor < this.valor) {
			// esquerda
			if(esquerda == null) esquerda = new Arvore(valor, null, null);
			else esquerda.adicionarElemento(valor);
		} else if(valor > this.valor) {
			// direita
			if(direita == null) direita = new Arvore(valor, null, null);
			else direita.adicionarElemento(valor);
		}
	}
	
	public static Arvore doArrayOrdenado(int[] arr) { 
		return criarOrdenado(arr, 0, arr.length-1);
	}
	
	public static Arvore criarOrdenado(int[] arr, int inicio, int fim) {
		if(inicio >= fim) return null;
		
		int meio = (inicio + fim) / 2;
		
		Arvore arvore = new Arvore(arr[meio], null, null);
		arvore.esquerda = criarOrdenado(arr, inicio, meio-1);
		arvore.direita = criarOrdenado(arr, meio+1, fim);
		
		return arvore;
	}
	
	public static void main(String[] args) {
		// 1. Instanciar uma árvore com alguns filhos
		Arvore raiz = new Arvore(5,null,null);
		raiz.adicionarElemento(3);
		raiz.adicionarElemento(7);
		raiz.adicionarElemento(1);
		raiz.adicionarElemento(2);
		raiz.adicionarElemento(6);
		raiz.adicionarElemento(8);
		raiz.adicionarElemento(9);
		raiz.adicionarElemento(10);
		raiz.adicionarElemento(11);
		raiz.adicionarElemento(4);
		
		// 2. Atravessar todos elementos escrevendo na tela
		raiz.atravessar();
	}

}
