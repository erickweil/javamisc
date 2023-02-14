/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sorting;

/**
 *
 * @author erick
 */
public class Sorting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //                        0 1 2 3 4 5 6 7 8 9 
        int[] numeros = new int[]{1, 57, 38, 30, 90, 17, 87, 16, 89, 76};
        
        System.out.print("Antes:\t");
        printar(numeros);
        
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
        
        System.out.print("Depois:\t");
        printar(numeros);
    }
    
    public static void printar(int[] arr) {
        for(int k =0;k < 10; k++)
        {
            System.out.print(arr[k]+", ");
        }
        System.out.println("");
    }
    
}
