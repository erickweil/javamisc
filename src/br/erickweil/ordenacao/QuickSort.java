package br.erickweil.ordenacao;

import java.util.Arrays;

/**
 * <p><h2>Resumo do algoritmo</h2>
 * </p><p>
 * Sua ideia básica consiste em aplicar a técnica de divisão e conquista,
 * separando a sequência de elementos em subsequências menores, inicialmente em
 * duas partes: uma com os elementos menores que o elemento escolhido para
 * comparação e outra com os maiores. De forma recursiva, o algoritmo separa
 * essas partes repetidamente até que estas fiquem cada vez menores e os
 * elementos sejam ordenados.
 * </p><p>
 * <h2>Etapas para implementação</h2>
 * </p><p>
 * Assumindo que a sequência de elementos seja um vetor com pelo menos dois
 * elementos:
 * </p><p>
 * Escolher um elemento do vetor chamado de pivô;
 * </p><p>
 * Declarar dois ponteiros: um para o início da lista e outro para o fim;
 * </p><p>
 * Enquanto o ponteiro do início for menor que o pivô, incrementa um. O
 * contrário acontece com o ponteiro do fim: diminui um enquanto ele for maior
 * ou igual ao pivô;
 * </p><p>
 * Se não satisfeitas essas condições, deve-se trocar os elementos nas posições
 * dos ponteiros;
 * </p><p>
 * Após a troca, avançar o ponteiro do início e decrementar o ponteiro do fim
 * para analisar os próximos elementos;
 * </p><p>
 * Quando os ponteiros se encontram, isto é, quando o ponteiro do início for
 * menor ou igual ao do fim, a repetição para;
 * </p><p>
 * O vetor é dividido em outros dois vetores: um com números menores que o pivô
 * e outro com os números maiores.
 * </p><p>
 * Executa o algoritmo novamente (de forma recursiva) para os dois vetores,
 * diminuindo cada vez mais até que todos estejam ordenados;
 * </p><p>
 * Concatenar os vetores, produzindo um único vetor ordenado.
 * </p><p>
 * <h2>Implementação</h2>
 * </p><p>
 * Código no GitHub: https://github.com/jaina-egler/QuickSort-em-Java/
 * </p><p>
 * <h2>Complexidade</h2>
 * </p><p>
 * Em seu pior caso, o Quicksort tem complexibilidade quadrática: O(n²). No
 * melhor e em caso médio, ele opera em O(n log n). O desempenho do algoritmo
 * depende da partição dos vetores serem balanceados ou não balanceados, isto é,
 * de mesmo tamanho e da escolha do pivô. Nesse caso, sendo a escolha do pivô o
 * primeiro elemento e as partições de tamanhos diferentes, o primeiro primeiro
 * índice varia de 0 a n para cada um dos valores do outro índice.
 * </p><p>
 * Uma maneira de melhorar sua complexidade, é escolher o pivô como metade do
 * vetor. Assim as comparações serão feitas de forma mais prática, visto que o
 * vetor será dividido ao meio e depois em partes cada vez menores.
 * </p>
 * @author Jaina Dady Egler Cardoso
 * @author Priscila Hurtado Teodoro
 */
public class QuickSort implements Ordenador{
	
	@Override
	public void ordenar(int[] array) {
		// TODO Auto-generated method stub
		quickSort(array, 0, array.length - 1);
	}

	public static void main(String[] args) {
		// Criando o array
		int[] array = new int[100000];

		// Preenchendo o array com valores aleatórios
		for (int i = 0; i < array.length; i++) {
			array[i] = (int) Math.floor(Math.random() * array.length);
		}

		// ATENÇÃO - Preenchendo o array com valores ordenados, se descomentar esse
		// trecho de código
		// e utilizar ele pra gerar os valores do array, aparece o erro stack overflow
		// (pelo menos na minha máuqina)
		// devido ao fato de que as partições do array ficam muito desbalanceadas.
		// for (int i = 0; i < array.length; i++) {
		// array[i] = i;
		// }

		System.out.println(
				"Array desordenado\nInício da lista:" + comecoArray(array) + "\nFinal da lista: " + fimArray(array));

		// Contabilizando tempo inicial de execução do programa
		long tempoInicial = System.currentTimeMillis();

		// Chamando o quicksort, passando o array o indice 0 e o último indice
		quickSort(array, 0, array.length - 1);

		// Contabilizando tempo inicial de execução do programa
		long tempoFinal = System.currentTimeMillis();

		System.out.println(
				"\nArray ordenado\nInício da lista:" + comecoArray(array) + "\nFinal da lista: " + fimArray(array));
		System.out.println("Tempo de execução(ms):" + (tempoFinal - tempoInicial));
	}

	public static String comecoArray(int[] array) {
		String comecoListaFinal = "";
		for (int x = 0; x < 6; x++) {
			String comecoLista = array[x] + "...";
			comecoListaFinal += comecoLista;
		}

		return comecoListaFinal;
	}

	public static String fimArray(int[] array) {
		String fimListaFinal = "";
		for (int x = array.length - 1; x > array.length - 5; x--) {
			String fimLista = array[x] + "...";
			fimListaFinal += fimLista;
		}

		return fimListaFinal;
	}

	public static void quickSort(int[] array, int start, int end) {
		// Verifica se o início do array é menor que o final
		if (start < end) {

			// Chama a função que
			int pivotIndex = partition(array, start, end);
			quickSort(array, start, pivotIndex - 1);
			quickSort(array, pivotIndex + 1, end);
		}
	}

	public static int partition(int[] array, int start, int end) {
		// Define o pivot como o primeiro elemento do array
		int pivot = array[start];

		// Indice do pivot + 1
		int i = start + 1;

		// Último elemento do array
		int j = end;

		// Enquanto o começo for menor ou igual ao final
		while (i <= j) {

			// Verificar se o array na posição i é menor ou igual ao pivot atual
			if (array[i] <= pivot) {
				// Se for, adiciona +1 ao indice inicial
				i++;
			}

			// Se não for, verifica se o array na posição final é maior que o pivot
			else if (array[j] > pivot) {
				// Se verdadeiro, retira 1 do indice fina
				j--;
			} else {
				// Senão, chama a função
				trocar(array, i, j);
			}
		}

		trocar(array, start, j);
		return j;
	}

	public static void trocar(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}