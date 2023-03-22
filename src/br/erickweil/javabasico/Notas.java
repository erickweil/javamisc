package br.erickweil.javabasico;

public class Notas {

	/**
	 * A ideia é separar o dinheiro em notas, de tal forma que o mínimo de
	 * notas seja oferecido.
	 * 
	 * O operador % representa o resto da divisão. Onde que 15 % 12 é igual a 3, pois 
	 * ao dividir 15 por 12, sobra 3
	 * 
	 * Também, quando se divide / dois números inteiros, o resultado continua sendo um inteiro
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int dinheiro = 758;

		int notas100 = dinheiro / 100;
		dinheiro = dinheiro % 100;
		
		int notas50 = dinheiro / 50;
		dinheiro = dinheiro % 50;
		
		int notas20 = dinheiro / 20;
		dinheiro = dinheiro % 20;
		
		int notas10 = dinheiro / 10;
		dinheiro = dinheiro % 10;
		
		int notas5 = dinheiro / 5;
		dinheiro = dinheiro % 5;
		
		int notas2 = dinheiro / 2;
		dinheiro = dinheiro % 2;

		if(notas100 > 0)
		System.out.println(notas100+" Notas de R$ 100,00");
		
		if(notas50 > 0)
		System.out.println(notas50+" Notas de R$ 50,00");
		
		if(notas20 > 0)
		System.out.println(notas20+" Notas de R$ 20,00");
		
		if(notas10 > 0)
		System.out.println(notas10+" Notas de R$ 10,00");
		
		if(notas5 > 0)
		System.out.println(notas5+" Notas de R$ 5,00");
		
		if(notas2 > 0)
		System.out.println(notas2+" Notas de R$ 2,00");
		
		if(dinheiro > 0)
		System.out.println(dinheiro+" Moedas de R$ 1,00");
	}

}
