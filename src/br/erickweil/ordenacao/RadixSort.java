package br.erickweil.ordenacao;

/**
 *  <h1>Radix sort</h1>
	
	<p> Radix sort é um jeito muito útil de se orgnanizar valores, letras e até chaves hasheadas, 
	porém mais especificadamente para números nessa explicação. O radix sorte leva em sua implementação algo como um bucket,
	um balde que é representado por um array onde vai a representação de onde vai aquele determinado elemento. 
	O radix sort vai de trás para frente organizando por unidade, dezenas, centenas e etc. quando pensamos em complexidade de tempo temos um dos mais lentos, 
	por isso deve ser usado em momentos muito específicos quando temos range pequenos de números. Voltando aos buckets. supondo que o usuário coloque 3 números 
	como entrada 123, 127, 120, radix sort irá olhar primeiro 3, 7, 0 e logo após isso 2, 2, 2 e por fim 1, 1, 1. Dessa forma irá fazer a ordenação.
	Usa-se o counting sort para auxiliar na implementação desse método de ordenação. 
	</p>
	
	<p> paso-a-passo
	<ol>
	<li>definir um array e salvar o seu tamanho total;</li>
	<li>pegar o maior valor dentro do array;</li>
	<li>passar para o counting sort analisar e fazer as operações de mapear dentro do bucket os válores, calcular o valor aculmulado e colocar os elementos do array passado para ele em ordem;</li>
	<li>criar uma função para printar o array já ordenado;</li>
	</ol>
	</p>
 * @author Guilherme Santos Alves Pinto
 *
 */
public class RadixSort implements Ordenador {
	
	@Override
	public void ordenar(int[] array) {
		// TODO Auto-generated method stub
		radixsort(array, array.length);
	}

    private static int getMax(int myarray[], int n) {
        int mx = myarray[0];
        for (int i = 1; i < n; i++)
            if (myarray[i] > mx)
                // get always the greatest
                mx = myarray[i];
        return mx;
    }

    private static void countSort(int arr[], int n, int exp) {
        int output[] = new int[n]; // new array of n positions
        int bucket[] = new int[10];
        //Arrays.fill(bucket, 0); isso n precisa ok, int[] já começa com 0

        // map inside the bucket
        for (int i = 0; i < n; i++) {
            bucket[(arr[i] / exp) % 10] = bucket[(arr[i] / exp) % 10] + 1;
        }

        // Calculate cumulative bucket
        for (int j = 1; j < 10; j++) {

            bucket[j] = bucket[j] + bucket[j - 1];
        }

        // placing elements in sorted order
        for (int k = n - 1; k >= 0; k--) {
            // printing
            // for(int l = 0; l < bucket.length; l++){
            //     System.out.print(bucket[l]);
            // }
            // System.out.println("-----");
            // for(int l = 0; l < output.length; l++){
            //     System.out.print(output[l]);
            // }
            output[bucket[(arr[k] / exp) % 10] - 1] = arr[k];
            bucket[(arr[k] / exp) % 10]--;
            // System.out.println("");
            // System.out.println("");
       
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }

    }

    private static void radixsort(int arr[], int n) {
        int max = getMax(arr, n);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    private static void printing(int[] arr){
        for(int el : arr){
            System.out.print(el+", ");
        }
    }

    public static void main(String[] args) {
        int myarray[] = { 250, 222, 555 };
        int n = myarray.length;

        // function call
        radixsort(myarray, n);

        printing(myarray);
        // printing
        // for (int l = 0; l < myarray.length; l++) {
        //     System.out.println(myarray[l]);
        // }
    }
}
