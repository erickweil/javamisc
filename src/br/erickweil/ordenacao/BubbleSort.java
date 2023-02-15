package br.erickweil.ordenacao;

public class BubbleSort {
	public static void main(String[] args) {
        //                        0 1 2 3 4 5 6 7 8 9 
        int[] numeros = new int[40000];
        for(int k =0;k < numeros.length; k++)
        {
            numeros[k] = numeros.length - k;
        }
        
        System.out.print("Antes:\t");
        printar(numeros);
        
        
        long tempoInicio = System.nanoTime();
        
        int ntrocas;
        do {
            ntrocas = 0;
            // atravessa cada par de numeros
            for(int i = 0;i < numeros.length-1; i++)
            {
                int atual = i;
                int prox = i+1;

                // Se não está em ordem
                if(numeros[atual] > numeros[prox])
                {
                    // Troca
                    int temp = numeros[prox];
                    numeros[prox] = numeros[atual];
                    numeros[atual] = temp;
                    ntrocas++;
                }
            }
        }
        while(ntrocas > 0);
        
        long tempoFim = System.nanoTime();
        
        System.out.print("Depois:\t");
        printar(numeros);
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("Tempo de Execução: %.2f milissegundos \n",tempoDiff);
        
        
    }
    
    public static void printar(int[] arr) {
        for(int k =0;k < 10; k++)
        {
            System.out.print(arr[k]+", ");
        }
        System.out.println("");
    }
}
