package br.erickweil.javabasico;

public class Recursividade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		teste(3);
		
		System.out.println("Fatorial de 5:"+fatorial(5));
	}

	// 5 = 5 * 4 * 3 * 2 * 1
	// F(n) = n * F(n-1)
	// F(1) = 1
	public static double fatorial(int n) {
		double ret;
		
		if(n == 1) ret = 1.0;
		else ret = n * fatorial(n-1);
		
		System.out.println("fatorial("+n+") ret:"+ret);
		return ret;
	}

	
	public static void teste(int i) {
		System.out.println("Inicio teste:"+i);
		if(i > 0) {
			teste(i - 1);
		} else {
			System.out.println("Parou de chamar.");
		}
		System.out.println("Fim teste:"+i);
	}

}
