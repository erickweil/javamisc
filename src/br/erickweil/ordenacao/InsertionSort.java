package br.erickweil.ordenacao;

import br.erickweil.utilidades.Arrays;

public class InsertionSort {

	// A ideia é re-inserir um elemento no array, por trocá-lo de posição
	
	// A ideia é incluir um por um elementos da área não ordenada para a área ordenada.
	// Começa considerando o primeiro elemento como uma área em ordem
		
	// Como cada elemento é incluído:
	// 1. o elemento é 'removido' da parte não ordenada e guardado em uma variável
	// 2. A parte ordenada é 'expandida' para ocupar o espaço que o elemento removido liberou
	// 3. Deslocar todos os elementos da parte ordenada maiores que o elemento removido para a direita
	// 4. inserir o elemento removido no espaço vazio que foi criado
	
	// Rápido quando quase todos estão já em ordem.
	public static void ordenar(int[] numeros)
	{
		// Começa em 1, primeiro elemento da parte não ordenada
        for(int i = 1;i < numeros.length;i++)
        {
            // Valor do elemento que deve ser inserido
            int inserir = numeros[i];
            
            // Deslocar todos os elementos maiores para a direita
            int k;
            for(k = i - 1;k >= 0;k--)
            {
            	if(numeros[k] <= inserir)
            		break; // achau um que não é maior, parando
            	
            	numeros[k+1] = numeros[k]; // Desloando para a direita
            }
            
            // O valor (k+1) será o espaço vazio pois k é o índice do elemento que não é maior ou então -1 quando moveu todos.
            numeros[k+1] = inserir;
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
