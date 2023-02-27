package br.erickweil.ordenacao;

import br.erickweil.utilidades.Arrays;

public class SelectionSort {

	// A ideia ir andando no array e encontrar o menor elemento e trocá-lo pela posição do atual.
	
	// 1. Começa no primeiro. procura por um menor na lista. se encontrar, troca.
	// Repete o processo andando para a direita
	public static void ordenar(int[] numeros)
	{
		for(int i=0;i<numeros.length;i++)
		{
			int menor = numeros[i];
			int menor_pos = i;
			for(int k = i+1;k<numeros.length;k++)
			{
				if(numeros[k] < menor)
				{
					menor = numeros[k];
					menor_pos = k;
				}
			}
			
			// Trocar o i pelo menor se não for ele mesmo
			if(menor_pos != i)
			{
				// 'menor' já é a variável temporária do swap
				numeros[menor_pos] = numeros[i];
				numeros[i] = menor;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] numeros = new int[1000];
        Arrays.iniciarAleatorio(numeros, 500);
        
        Arrays.printar(numeros);
                
        long tempoInicio = System.nanoTime();
        
        ordenar(numeros);
        
        long tempoFim = System.nanoTime();
        
        Arrays.printar(numeros);
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("Tempo de Execução: %.2f milissegundos \n",tempoDiff);   
	}

}
