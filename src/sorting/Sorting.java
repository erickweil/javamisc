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
        int[] numeros = new int[]{2,4,7,3,8,4,9,1,0,6};
        
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
