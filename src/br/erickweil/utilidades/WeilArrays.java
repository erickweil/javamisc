package br.erickweil.utilidades;

import java.util.Random;

public class WeilArrays {
    
	/**
	 * Inicia o array com valores aleatórios entre 0 e maximo
	 */
	public static void iniciarAleatorio(int[] arr,int maximo) {
		Random rdn = new Random();
		
		for(int k =0;k < arr.length; k++)
        {
			arr[k] = rdn.nextInt(maximo);
        }
	}

	/**
	 * Inicia o array com valores aleatórios entre 0 e maximo, sem repetir
	 */
	public static void iniciarAleatorioUnico(int[] arr,int maximo) {
		Random rdn = new Random();
		
		for(int k =0;k < arr.length; k++)
        {
			int n;
			int tentativas = 100;
			boolean unico;
			do {
				unico = true;
				n = rdn.nextInt(maximo);
				for(int i =0;i < k; i++)
		        {
					if(arr[i] == n) unico = false;
		        }
				tentativas--;
			} while(!unico && tentativas >= 0);
			arr[k] = n;
        }
	}
	
	/**
	 * Printa o array na tela, cortando arrays maiores que 100 elementos.
	 */
    public static void printar(int[] arr) {
    	int max = arr.length;
    	if(max > 100) max = 100;
        printar(arr,max);
    }	
	
	/**
	 * Printa os elementos do array na tela, até o tamanho 'len'
	 */
    public static void printar(int[] arr,int len) {
    	
    	System.out.print("[");
    	System.out.print(arr[0]);
        for(int k =1;k < len; k++)
        {
        	System.out.print(", ");
            System.out.print(arr[k]);
        }
        if(arr.length > len) System.out.print(", +"+(arr.length - len)+" elementos...");
        System.out.println("]"); // ln para Pular linha para terminar
    }
}
