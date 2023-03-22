package br.erickweil.ordenacao;

/**
 * 
    <p>O algoritmo Selection Sort funciona da seguinte forma:
	a cada iteração, ele busca o menor valor do array e o coloca na primeira posição, 
	depois busca o segundo menor valor e o coloca na segunda posição, e assim por diante até que todos os valores estejam em ordem crescente.
	Ele faz isso comparando cada elemento com os outros do array e trocando de posição aquele que é o menor, até que o array esteja completamente ordenado. 
	É como se o algoritmo fosse "selecionando" o menor valor em cada iteração e colocando na posição correta, daí o nome "Selection Sort". </p>
    
    <p> O algoritmo Selection Sort é executado em duas etapas:
    <ol>
    <li>Buscar o menor valor: o algoritmo começa comparando o primeiro valor do array com todos os outros valores para encontrar o menor valor. 
    Se ele encontra um valor menor, ele guarda a posição desse valor.</li>
	
	<li>Colocar o menor valor na primeira posição: depois que o menor valor é encontrado, ele é trocado com o valor na primeira posição do array. 
	O algoritmo então repete o processo para encontrar o segundo menor valor e o coloca na segunda posição, e assim por diante até que todo o array esteja ordenado. 
	Em cada iteração, o algoritmo sempre procura pelo menor valor entre os valores que ainda não foram ordenados.</li>
	</ol>
	</p>
	
	<p>A complexidade de tempo do Selection Sort é O(n^2), onde n é o tamanho do array. Isso ocorre porque ele tem dois loops aninhados, que percorrem todo o array. 
	A cada iteração do loop externo, o loop interno é executado n-i vezes, já que i elementos já foram ordenados. Portanto, a complexidade é n*(n-1)/2, que é O(n^2).</p>

	<p>A complexidade de espaço do Selection Sort é O(1), pois ele não utiliza nenhuma estrutura de dados adicional para armazenar os elementos. 
	Ele apenas utiliza variáveis temporárias para trocar os elementos de posição no array.</p>

	<p>Uma das razões para o algoritmo de ordenação Selection Sort ter uma complexidade de tempo tão alta é porque ele faz muitas comparações desnecessárias. 
	Mesmo que um elemento já esteja na posição correta, ele ainda assim será comparado com todos os outros elementos. Isso pode tornar o algoritmo ineficiente em arrays grandes.</p>
	
 * @author Wanilk Caldas de Andrade
 */
public class SelectionSort implements Ordenador{

	@Override
	public void ordenar(int[] array) {
		selectionSort(array);
	}
	
    public static void main(String[] args) {
        int[] arr = { 5, 4, 3, 2, 1, 7, 8, 9, 6 };
        selectionSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }
}
