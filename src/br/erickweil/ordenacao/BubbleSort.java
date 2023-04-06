package br.erickweil.ordenacao;

import br.erickweil.utilidades.WeilArrays;

/**
 * Implementação do algoritmo BubbleSort <br/>
 *  <br/>
 * Complexidade de tempo no pior caso: O(n²) <br/>
 *   <br/>
 * A ideia é atravessar cada par do array e trocar a posicao quando não estiver em ordem  <br/>
 * repetir até que o array inteiro esteja em ordem  <br/>
 * OK - Um otimização é parar o algoritmo assim que não houver nenhuma troca  <br/>
 * A fazer - Outra otimização é não atravessar até o fim do array toda vez, já que cada iteração coloca 1 elemento em ordem pelo menos  <br/>
 * 
 * @author Erick Leonardo Weil
 */
public class BubbleSort implements Ordenador {
	
	// Ordena o array passado, não cria outro apenas altera o que foi provido.
	public void ordenar(int[] numeros)
	{
        int ntrocas;
        // O Do-While irá parar quando não houver mais trocas em uma iteração, indicando que está em ordem
        for(int k = numeros.length-1;k > 1; k--) {
            ntrocas = 0;
            // atravessa cada par de numeros
            // length-1 para o prox que é i+1 não passar do último índice
            for(int i = 0;i < k; i++)
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
            
            if(ntrocas == 0)
            {
            	break; // Não precisa mais repetir. já está em ordem.
            }
        }
	}
	
	/**
	 * Testando o método de ordenação BubbleSort com um array de 40000 elementos
	 * @param args
	 */
	public static void main(String[] args) {
        //  0 1 2 3 4 5 6 7 8 9 
        int[] numeros = new int[1000];
        WeilArrays.iniciarAleatorio(numeros, 500);
        
        System.out.print("Antes:\t");
        WeilArrays.printar(numeros);
        
        long tempoInicio = System.nanoTime();
        
        new BubbleSort().ordenar(numeros);
        
        long tempoFim = System.nanoTime();
        
        System.out.print("Depois:\t");
        WeilArrays.printar(numeros);
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("Tempo de Execução: %.2f milissegundos \n",tempoDiff);   
    }
}
