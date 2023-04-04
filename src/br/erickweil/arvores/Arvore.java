package br.erickweil.arvores;

public class Arvore {

	int valor;
	Arvore direita;
	Arvore esquerda;
	
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
		} else {
			// direita
			if(direita == null) direita = new Arvore(valor, null, null);
			else direita.adicionarElemento(valor);
		}
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
