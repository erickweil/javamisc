package br.erickweil.javabasico;

public class TesteAB {

	public static void main(String[] args) {
		/**
		 * Atribuindo valores em variáveis
		 * O valor de A começa valendo 3
		 * 3 então é COPIADO em B
		 * e por último o valor de A passa a valer 100
		 * porém veja que B não é afetado e continua valendo 3
		 * */
		int a = 3;
		int b = a;
		a = 100;
		
		// Veja que a variável b continua valendo 3
		System.out.println("Variáveis A e B:");
		System.out.println("A:"+a);
		System.out.println("B:"+b);

		/**
		 * Atribuindo valores em arrays
		 * O valor de A começa valendo [1,2,3]
		 * O valor de B passa a ser o mesmo array. (O MESMO OBJETO) 
		 * O valor da posição 1 do array passa a valer 77
		 * veja que como B é o MESMO OBJETO, O array B também vale 77 na posição 1
		 * */
		int[] array_a = new int[] {1,2,3};
		int[] array_b = array_a;
		
		array_a[1] = 77;

		System.out.println("Arrays A e B:");
		// Veja que no caso do array, os dois percebem a modificação que foi feita no objeto
		System.out.print("A: [");
		for(int v : array_a) System.out.print(v+", ");
		System.out.println("]");
		
		System.out.print("B: [");
		for(int v : array_b) System.out.print(v+", ");
		System.out.println("]");
	}

}
