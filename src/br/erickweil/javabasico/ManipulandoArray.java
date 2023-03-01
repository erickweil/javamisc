package br.erickweil.javabasico;

import java.util.Random;

public class ManipulandoArray {
	public static void main(String[] args) {
		int[] numeros = new int[50];
		
		Random rdn = new Random();
		for(int i = 0;i < numeros.length;i++)
		{
			numeros[i] = rdn.nextInt(0, 100);
		}
		
		for(int i = 0;i < numeros.length;i++)
		{
			System.out.println(numeros[i]);
		}
		
	}
}
