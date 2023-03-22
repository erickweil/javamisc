package br.erickweil.estruturas;

public class Referencias {

	public static class Elo {
		String nome;
		Elo proximo;
		
		public Elo(String nome) {
			this.nome = nome;
		}
	}
	
	public static void main(String[] args) {
		
		Elo primeiro = new Elo("Maria");
		
		primeiro.proximo = new Elo("João");

		Elo novo = new Elo("Hellen");
		novo.proximo = primeiro;
		primeiro = novo;
		
		Elo atual = primeiro;
		while(atual != null) {
			System.out.println(atual.nome);
			
			atual = atual.proximo;
		}
	}

}
