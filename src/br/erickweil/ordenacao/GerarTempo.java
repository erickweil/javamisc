package br.erickweil.ordenacao;

import br.erickweil.utilidades.Arrays;

public class GerarTempo {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i = 1;i < 100;i++) {
			testar(i * 1000, new BubbleSort());
		}
	}
	
	public static void testar(int n,Ordenador ord) {
        int[] numeros = new int[n];
        Arrays.iniciarAleatorio(numeros, n*10);
        
        long tempoInicio = System.nanoTime();
        
        ord.ordenar(numeros);
        
        long tempoFim = System.nanoTime();
        
        double tempoDiff = (tempoFim - tempoInicio)/1000000.0;
        System.out.printf("%d\t%.2f\n",n,tempoDiff);
	}
	

}
