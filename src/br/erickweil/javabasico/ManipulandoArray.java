package br.erickweil.javabasico;

public class ManipulandoArray {
	public static void main(String[] args) {
		int[] numeros = new int[50];
		
		for(int i = 0;i < numeros.length;i++) 
		{
			double aleatorio = Math.random() * 100;
			numeros[i] = (int) aleatorio;
		}
		
		int maior = numeros[0];
		int menor = numeros[0];
		int soma = 0;
		for(int i = 0;i < numeros.length;i++) 
		{
			if(numeros[i] > maior)
			{
				maior = numeros[i];
			}
			if(numeros[i] < menor)
			{
				menor = numeros[i];
			}
			soma = soma + numeros[i];
		}
		
		System.out.println("O maior de todos é:"+maior);
		System.out.println("O menor de todos é:"+menor);
		double media = soma / (double)numeros.length;
		System.out.println("A média é:"+media);
		
	}
}
