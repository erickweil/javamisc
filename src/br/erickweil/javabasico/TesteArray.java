package br.erickweil.javabasico;

public class TesteArray {

	public static void main(String[] args) {
		int[] arr = new int[10];
		// índices: 0 1 2 3 4 5 6 7 8 9
		
		arr[5] = 33;
		arr[8] = 77;
		
		// Jeito RIDÍCULO de escrever todos na tela
		System.out.print(arr[0]+", ");
		System.out.print(arr[1]+", ");
		System.out.print(arr[2]+", ");
		System.out.print(arr[3]+", ");
		System.out.print(arr[4]+", ");
		System.out.print(arr[5]+", ");
		System.out.print(arr[6]+", ");
		System.out.print(arr[7]+", ");
		System.out.print(arr[8]+", ");
		System.out.print(arr[9]);
		
		System.out.println("\n--------------------------------"); //Para pular linha
		// Escrever todos na tela usando while
		int contador = 0;
		while(contador < arr.length) {
			System.out.print(arr[contador]+", ");
			
			contador++;
		}
		System.out.println("\n--------------------------------"); //Para pular linha
				
		
		// Escrever todos na tela usando for com contador:
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+", ");
		}
		System.out.println("\n--------------------------------"); //Para pular linha
		
		// Escrever todos na tela usando for aprimorado:
		for (int valor : arr) {
			System.out.print(valor+", ");
		}
		System.out.println("\n--------------------------------"); //Para pular linha
		
	}

}
