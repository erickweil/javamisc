package br.erickweil.javabasico;

public class Notas {

	public static void main(String[] args) {
		int dinheiro = 18;
		
		int notas5 = dinheiro / 5;
		dinheiro = dinheiro % 5;
		
		int notas2 = dinheiro / 2;
		dinheiro = dinheiro % 2;
		if(notas5 > 0)
		System.out.println(notas5+" Notas de R$ 5,00");
		
		if(notas2 > 0)
		System.out.println(notas2+" Notas de R$ 2,00");
		
		if(dinheiro > 0)
		System.out.println(dinheiro+" Moedas de R$ 1,00");
	}

}
