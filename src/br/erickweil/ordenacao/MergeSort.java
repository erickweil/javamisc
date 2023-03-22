package br.erickweil.ordenacao;

/**
 * <p><h2>Merge Sort</h2>
 * </p><p>
 * Merge Sort consiste em um algoritmo de ordenação que para o pior ou o melhor
 * casa, ele funciona da mesma forma e custando o mesmo tempo. Ele usa um vetor
 * auxiliar durante a ordenação, que consequentemente exige mais tempo e
 * memória.
 * </p><p>
 * O Merge Sort recebe como parâmetro o vetor a ser ordenado e ele,
 * resumidamente, é separado em duas etapas: a primeira consiste em dividir o
 * vetor em partes menores, aí que vem a chamada “dividir para conquistar”, onde
 * se divide um vetor em partes menores, até que não possa mais dividir para
 * assim facilitar a ordenação.
 * </p><p>
 * A primeira etapa o merge consiste em dividir o vetor em partes menores e
 * organizá-los nas variáveis início e fim, para fazer essa manipulação usamos a
 * o array auxiliar aux.
 * </p>
 * @author Yasmin Gabrielly Peres Cruz
 * @author Luciano Gomes da Silva
 */
public class MergeSort implements Ordenador {

	/**
	 * Função que deve ser chamada para ordenar:
	 */
	@Override
	public void ordenar(int[] array) {
		mergeSort(array,0,array.length-1);
	}
	
	public void merge(int[] vetor, int inicio, int meio, int fim) {
		// transfere os elementos entre inicio e fim para um array auxiliar = aux.
		int[] aux = new int[vetor.length];
		for (int i = inicio; i <= fim; i++) {
			aux[i] = vetor[i];
		}

		/**
		 * Em seguida são definidas as variáveis i, j e k que vão servir para controle
		 * da execução e comparação dos elementos. i marca o início da primeira parte do
		 * array, j marca o início da segunda parte do array e k marca a posição em que
		 * o menor elemento entre aux[i] e aux[j] deve ser adicionado.
		 */

		int i = inicio;
		int j = meio + 1;
		int k = inicio;

		/**
		 * A seguir, o algoritmo começa a comparação entre aux[i] e aux[j] para
		 * adicionar o menor elemento em vetor[k].
		 */

		while (i <= meio && j <= fim) {
			if (aux[i] <= aux[j]) {
				vetor[k] = aux[i];
				i++;
			} else {
				vetor[k] = aux[j];
				j++;
			}
			k++;
		}

		/**
		 * Ao fim, uma das partes do array será terminada antes da outra. Para isso,
		 * usamos o append de todos os elementos da parte que não foi completamente
		 * consumida
		 */

		// se a metade inicial não foi toda consumida, faz o append.
		while (i <= meio) {
			vetor[k] = aux[i];
			i++;
			k++;
		}

		// se a metade final não foi toda consumida, faz o append.
		while (j <= fim) {
			vetor[k] = aux[j];
			j++;
			k++;
		}
	}

	/**
	 * A segunda parte, o Merge Sort é um algoritmo de divisão-e-conquista. A parte
	 * da conquista se concentra na primeira parte do algoritmo, o merge. Então
	 * nessa segunda parte, e exclusivamente para a divisão, para isso usamos os
	 * índices início, meio e fim para ter um controle das partes do array em que o
	 * algoritmo irá mexer.
	 */

	public void mergeSort(int[] vetor, int inicio, int fim) {
		if (inicio >= fim)
			return;
		else {
			int meio = (inicio + fim) / 2;
			mergeSort(vetor, inicio, meio);
			mergeSort(vetor, meio + 1, fim);
			merge(vetor, inicio, meio, fim);
		}
	}

	/**
	 * Nos parâmetros do método megeSort estão: o próprio array a ser ordenado, o
	 * índice início e um índice fim, que delimita a parte do array que o algoritmo
	 * deve analisar. No primeiro momento, temos início = 0 e fim = vetor.length -1.
	 * Em seguida, na primeira linha do código, temos a condição de parada (inicio
	 * >= fim). Essa linha pode ser explicada da seguinte forma: quando a parte do
	 * algoritmo a ser analisada possuir apenas um elemento, não há mais a
	 * necessidade de dividi-lo.
	 *
	 * 
	 * int meio = (inicio + fim) / 2; mergeSort(vetor, inicio, meio);
	 * mergeSort(vetor, meio + 1, fim); merge(vetor, inicio, meio, fim);
	 * 
	 * Na primeira linha, define o meio como sendo o valor central entre início e
	 * fim. A segunda e a terceira linha do são chamadas recursivas para a metade da
	 * esquerda(de início até meio) e para a metade da direita(do meio + 1 até fim)
	 * E finalizando o processo, após cada quebra há uma chamada ao método merge,
	 * passando os limites a serem considerados(inicio, meio, fim). O Merge Sort tem
	 * duas chamadas recursivas, cada uma com o objetivo de reduzir os
	 * problemas(tamanho do array) na metade. Então ele nos garante eficiência de n*
	 * log n. Alunos: Luciano e Yasmin
	 */
}