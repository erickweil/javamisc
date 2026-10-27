package br.erickweil.utilidades;

import java.util.Random;

public class WeilArrays {
    
	/**
	 * Inicia o array com valores aleatórios entre 0 e maximo-1
	 */
	public static void iniciarAleatorio(int[] arr,int maximo) {
		Random rdn = new Random();
		
		for(int k =0;k < arr.length; k++)
        {
			arr[k] = rdn.nextInt(maximo);
        }
	}

	/**
	 * Inicia o array com valores aleatórios entre 0 e maximo-1, sem repetir.
	 * Atenção: se maximo for menor que o tamanho do array não há valores suficientes,
	 * então depois de 100 tentativas o valor é aceito mesmo repetido.
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
        for(int k =0;k < len; k++)
        {
        	if(k > 0) System.out.print(", ");
            System.out.print(arr[k]);
        }
        if(arr.length > len) System.out.print((len > 0 ? ", " : "")+"+"+(arr.length - len)+" elementos...");
        System.out.println("]"); // ln para Pular linha para terminar
    }
}
