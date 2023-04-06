package br.erickweil.ordenacao;

import br.erickweil.utilidades.WeilArrays;

public class BuscaBinaria {
    
    public static int busca(int[] arr, int inicio, int fim, int objetivo) {
    	System.out.println("Chamou busca, inicio:"+inicio+" fim:"+fim);
    	// Evita possíveis erros
        if (inicio > fim)
        	return -1;
    
        /**
        int meio = (inicio + fim) / 2;
        
        https://www.youtube.com/watch?v=MFhxShGxHWc -> comentário de mephi5to
	        The code shown actually have a bug. When it was written back in the day it was not a problem. 
	        Now of your search array has billion elements the sum of integers may overflow and become negative. 
	        The new proper way to avoid going out of bounds of the integer to calculate mid point is 
	        
	 		int mid = low + ((high - low) / 2);
			This was a bug in Java for 9 years
        */
        int meio = inicio + (fim - inicio) / 2;
        int atual = arr[meio];
        
        if (atual == objetivo)
            return meio;
        else if (atual > objetivo)
            return busca(arr, inicio, meio - 1, objetivo);
        else
        	return busca(arr, meio + 1, fim, objetivo);
    }
    
    public static void main(String[] args) {

        int objetivo = 3333;
        
    	int[] arr = new int[10000];
        WeilArrays.iniciarAleatorio(arr, 10000);
        arr[0] = objetivo;
        new QuickSort().ordenar(arr);
        
        
        int posicao = busca(arr, 0, arr.length - 1, objetivo);
        
        if (posicao == -1) {
            System.out.println("O elemento não está presente no array.");
        } else {
            System.out.println("O elemento está presente na posição " + posicao);
        }
    }
}
