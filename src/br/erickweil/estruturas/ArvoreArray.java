package br.erickweil.estruturas;

public class ArvoreArray {

	public static class Galho {
		String valor;
		int indice;
		int pai;
		int esquerda;
		int direita;
		
		public Galho(String valor) {
			this.valor = valor;
			this.pai = -1;
			this.indice = -1;
			this.esquerda = -1;
			this.direita = -1;
		}
		
		@Override public String toString() {
			return "["+valor+","+pai+","+esquerda+","+direita+"]";
		}
	}
	
	public int raiz;
	public Galho[] galhos;
	public int n;
	
	public void print() {
		for(Galho g: galhos) {
			if(g == null) break;
			System.out.println(g);
		}
	}
	
	public int addGalho(Galho g,int pai) {
		galhos[n] = g;
		g.indice = n;
		g.pai = pai;
		n++;
		
		return g.indice;
	}
	
	public int addElemento(String valor, int qual) {		
		if(raiz == -1) {
			raiz = addGalho(new Galho(valor),-1);
			return raiz;
		}
		
		Galho galho = galhos[qual];		
		
		if(galho.valor.compareTo(valor) < 0) {
			if(galho.esquerda == -1) {
				galho.esquerda = addGalho(new Galho(valor),qual);
				return galho.esquerda;
			} else {
				return addElemento(valor,galho.esquerda);
			}
		} else {
			if(galho.direita == -1) {
				galho.direita = addGalho(new Galho(valor),qual);
				return galho.direita;
			} else {
				return addElemento(valor,galho.direita);
			}
		}
	}
	
	public ArvoreArray() {
		galhos = new Galho[100];
		raiz = -1;
		n = 0;
	}
	
	public static void main(String[] args) {
		ArvoreArray arvore = new ArvoreArray();
		
		arvore.addElemento("João",arvore.raiz);
		arvore.addElemento("Maria",arvore.raiz);
		arvore.addElemento("Pedro",arvore.raiz);
		arvore.addElemento("Ana",arvore.raiz);
		arvore.addElemento("Carlos",arvore.raiz);
		arvore.addElemento("Juliana",arvore.raiz);
		arvore.addElemento("Fernando",arvore.raiz);
		arvore.addElemento("Gabriela",arvore.raiz);
		arvore.addElemento("Rafael",arvore.raiz);
		arvore.addElemento("Leticia",arvore.raiz);
		
		arvore.print();
	}

}
