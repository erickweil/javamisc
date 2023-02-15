package br.erickweil.utilidades;

import java.util.Random;

public class Arrays {
    
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
        for(int k =0;k < len; k++)
        {
        	System.out.print(", ");
            System.out.print(arr[k]);
        }
        if(arr.length > len) System.out.print(", +"+(arr.length - len)+" elementos...");
        System.out.println("]"); // ln para Pular linha para terminar
    }
}
