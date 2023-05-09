package br.erickweil.arvores;

public class ArvoreBinaria {

	ArvoreNo raiz;

	public ArvoreBinaria() {
		this.raiz = null;
	}
	
	public ArvoreBinaria(ArvoreNo raiz) {
		this.raiz = raiz;
	}
	
	public void escreverEmOrdem() {
		if(raiz == null) System.out.println("Árvore vazia");
		else raiz.escreverEmOrdem();
	}
	
	public void adicionarElemento(int numero) {
		if(raiz == null) {
			raiz = new ArvoreNo(numero);
		} else {
			raiz.adicionarElemento(numero);
		}
	}
	
	public boolean elementoExiste(int numero) {
		if(raiz == null) return false;
		else return raiz.elementoExiste(numero);
	}
	
	@Override
	public String toString() {
		return raiz == null ? "Árvore vazia" : raiz.toString();
	}

	public static class ArvoreNo {
		int valor;
		ArvoreNo esquerda;
		ArvoreNo direita;
		
		public ArvoreNo(int valor) {
			this.valor = valor;
		}
		
		public ArvoreNo(
				int valor, 
				ArvoreNo esquerda, 
				ArvoreNo direita) {
			this.valor = valor;
			this.esquerda = esquerda;
			this.direita = direita;
		}
		
		public void escreverEmOrdem() {
			if(esquerda != null)
			esquerda.escreverEmOrdem();
			
			System.out.println(this.valor);
			
			if(direita != null)
			direita.escreverEmOrdem();
		}
		
		public void adicionarElemento(int numero) {
			if(numero == this.valor) {
				return; // já existe.
			}
			if(numero < this.valor) {
				if(esquerda == null)
					esquerda = new ArvoreNo(numero);
				else
					esquerda.adicionarElemento(numero);
			} else { // maior
				if(direita == null)
					direita = new ArvoreNo(numero);
				else
					direita.adicionarElemento(numero);
			}
		}
		
		public boolean elementoExiste(int numero) {
			if(numero == this.valor) return true;
			else if(numero < this.valor) {
				if(esquerda == null) return false;
				else return esquerda.elementoExiste(numero);
			} else { // maior
				if(direita == null) return false;
				else return direita.elementoExiste(numero);
			}
		}
		
		@Override
		public String toString() {
			return "{valor:'"+valor+"',esquerda:"+esquerda+",direita:"+direita+"}";
		}
	}
	
	public static void main(String[] args) {
		ArvoreBinaria arvore = new ArvoreBinaria();
		arvore.adicionarElemento(5);
		arvore.adicionarElemento(8);
		arvore.adicionarElemento(3);
		arvore.adicionarElemento(7);
		arvore.adicionarElemento(9);
		arvore.adicionarElemento(1);
		arvore.adicionarElemento(4);
		
		System.out.println("Arvore binária:"+arvore);
		arvore.escreverEmOrdem();
		
		System.out.println("Existe 3? "+arvore.elementoExiste(3));
		System.out.println("Existe 99? "+arvore.elementoExiste(99));
	}
}
