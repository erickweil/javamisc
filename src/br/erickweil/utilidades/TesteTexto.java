package br.erickweil.utilidades;
public class TesteTexto {
	public static void main(String[] args) {
        String numeros = "[";
    	for(int i = 0; i < 200000; i++) {
        	String convertidoParaTexto = String.valueOf(i);
        	numeros += convertidoParaTexto;
        	numeros += ", ";
    	}
    	numeros += "]";
    	System.out.println(numeros.length());
    }
}
