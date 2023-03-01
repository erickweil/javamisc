package br.erickweil.javabasico;

public class Repetindo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Repetindo com While
		int contador = 0;
		while(contador < 10)
		{
			System.out.println("Repetiu "+contador+" vezes");
			contador = contador + 1;
		}
		
		// Repetindo com For
		for(int cont = 0; cont < 10; cont++)
		{
			System.out.println("Repetiu "+cont+" vezes");
		}

	}

}
