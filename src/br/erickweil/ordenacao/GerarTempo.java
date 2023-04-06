package br.erickweil.ordenacao;

import java.util.Scanner;

import br.erickweil.utilidades.WeilArrays;

public class GerarTempo {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Ordenador qual;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Insira qual algoritmo de ordenação quer testar:");
		System.out.println("1 - BubbleSort");
		System.out.println("2 - InsertionSort");
		System.out.println("3 - SelectionSort");
		System.out.println("4 - MergeSort");
		System.out.println("5 - RadixSort");
		System.out.println("6 - BucketSort");
		System.out.println("7 - QuickSort");
		
		int escolhido = Integer.parseInt(scan.nextLine());
		switch(escolhido) {
			case 1: qual = new BubbleSort(); break;
			case 2: qual = new InsertionSort(); break;
			case 3: qual = new SelectionSort(); break;
			case 4: qual = new MergeSort(); break;
			case 5: qual = new RadixSort(); break;
			case 6: qual = new BucketSort(); break;
			case 7: qual = new QuickSort(); break;
			default: 
				System.out.println("Escolheu um valor inválido, escolhendo BubbleSort...");
				qual = new BubbleSort(); break;
		}
		
		System.out.println("Quantas iterações deseja testar?");
		int iter = Integer.parseInt(scan.nextLine());
		
		System.out.printf("%s\t%s\n","ITER","MS");
		for(int i = 1;i < iter;i++) {
			testar(i * 1000, qual);
		}
	}
	
	public static void testar(int n,Ordenador ord) {
        int[] numeros = new int[n];
        WeilArrays.iniciarAleatorio(numeros, n*10);
        
        long tempoInicio = System.nanoTime();
        
        ord.ordenar(numeros);
        
        if(!checarOrdem(numeros)) {
        	System.out.println("Não ficou em ordem:");
        	WeilArrays.printar(numeros);
        }
        
        long tempoFim = System.nanoTime();
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("%d\t%.2f\n",n,tempoDiff);
	}
	
	public static boolean checarOrdem(int[] numeros) {
		int anterior = numeros[0];
		for(int v : numeros) {
			if(v < anterior) return false; 
			
			anterior = v;
		}
		
		return true;
	}
	

}
