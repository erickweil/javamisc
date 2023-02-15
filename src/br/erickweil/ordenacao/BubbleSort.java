package br.erickweil.ordenacao;

import br.erickweil.utilidades.Arrays;

/**
 * Implementação do algoritmo BubbleSort
 * 
 * Complexidade de tempo no pior caso: O(n²)
 * 
 * A ideia é atravessar cada par do array e trocar a posicao quando não estiver em ordem
 * repetir até que o array inteiro esteja em ordem
 * OK - Um otimização é parar o algoritmo assim que não houver nenhuma troca
 * A fazer - Outra otimização é não atravessar até o fim do array toda vez, já que cada iteração coloca 1 elemento em ordem pelo menos
 */

public class BubbleSort {
	
	// Ordena o array passado, não cria outro apenas altera o que foi provido.
	public static void ordenar(int[] numeros)
	{
        int ntrocas;
        // O Do-While irá parar quando não houver mais trocas em uma iteração, indicando que está em ordem
        do {
            ntrocas = 0;
            // atravessa cada par de numeros
            // length-1 para o prox que é i+1 não passar do último índice
            for(int i = 0;i < numeros.length-1; i++)
            {
                int atual = i;
                int prox = i+1;

                // Executa o bloco caso o par não esteja em ordem
                // Ex: i:0 [3,2] --> atual: 3 prox: 2 --> 3 > 2 --> true
                if(numeros[atual] > numeros[prox])
                {
                    // Troca. O atual recebe o próximo e o próximo recebe o atual
                    int temp = numeros[prox];
                    numeros[prox] = numeros[atual];
                    numeros[atual] = temp;
                    ntrocas++; // Registra que aconteceu uma troca.
                }
            }
        }
        while(ntrocas > 0);
	}
	
	/**
	 * Testando o método de ordenação BubbleSort com um array de 40000 elementos
	 * @param args
	 */
	public static void main(String[] args) {
        //  0 1 2 3 4 5 6 7 8 9 
        int[] numeros = new int[1000];
        Arrays.iniciarAleatorio(numeros, 10000);
        
        System.out.print("Antes:\t");
        Arrays.printar(numeros);
        
        long tempoInicio = System.nanoTime();
        
        ordenar(numeros);
        
        long tempoFim = System.nanoTime();
        
        System.out.print("Depois:\t");
        Arrays.printar(numeros);
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("Tempo de Execução: %.2f milissegundos \n",tempoDiff);   
    }
}