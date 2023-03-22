package br.erickweil.ordenacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>O algoritmo de Bucket Sort é responsável por organizar um array de elementos.
 * Para isso, o array é dividido em baldes, ou buckets, e o balde escolhido é
 * baseado no valor dos seus elementos.</p>
 * 
 * <p>Por exemplo um array com os elementos : { 30, 13, 23, 11, 33 } Digamos que o
 * array fosse divido em 5 baldes, cada um ficaria: Balde 1: { 11, 13 } Balde 2:
 * { } Balde 3: { } Balde 4: { 23 } Balde 5: { 30, 33 }</p>
 * 
 * <p>Os elementos são distribuídos conforme o intervalo entre o maior e menor
 * número do array, e após isso, os baldes são concatenados em ordem para formar
 * um novo array, com os elementos em ordem crescente.</p>
 *  
 * @author Carlos Felipe Steinheuser
 *
 */
public class BucketSort implements Ordenador {
	
	@Override
	public void ordenar(int[] array) {
		bucketSort(array, array.length/100 + 3);		
	}

	public static void bucketSort(int[] array, int n) {

		// Pega o elemento máximo e mínnimo do array
		double max = Arrays.stream(array).max().getAsInt();
		double min = Arrays.stream(array).min().getAsInt();
		
		// Compara a distância entre o máximo e mínimo
		double range = (max - min) / n;
		// Cria um ArrayList com os baldes, e instância uma nova lista para cada balde
		List<List<Integer>> buckets = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			buckets.add(new ArrayList<>());
		}

		// Distribui os elementos nos baldes baseado no valor do elemento
		for (int i = 0; i < array.length; i++) {
			// Calcula a diferença entre um número e sua parte inteira
			double diff = ((double)array[i] - min) / range - (int) (((double)array[i] - min) / range);
			// Checa se a diferença for 0 e o número for diferente do valor mínimo
			if (diff == 0 && (double)array[i] != min) {
				buckets.get((int) ((array[i] - min) / range) - 1).add(array[i]);
			}
			else {
				int indice = (int) ((array[i] - min) / range);
				if(indice >= buckets.size()) indice = buckets.size()-1;
				buckets.get(indice).add(array[i]);
			}
		}

		// Sorteia os baldes não vazios
		for (int i = 0; i < buckets.size(); i++) {
			if (!buckets.get(i).isEmpty()) {
				buckets.get(i).sort(Double::compare);
			}
		}

		// Concatena os valores no array original
		int index = 0;
		for (List<Integer> list : buckets) {
			if (!list.isEmpty()) {
				for (int i : list) {
					array[index] = i;
					index++;
				}
			}
		}
	}

	public static void main(String[] args) {
		int arr[] = { 42, 13, 78, 92, 11, 27 };
		bucketSort(arr, 3);
		System.out.println("Sorted array is ");
		for (double el : arr) {
			System.out.print(el + " ");
		}
	}
}
