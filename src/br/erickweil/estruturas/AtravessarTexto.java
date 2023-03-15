package br.erickweil.estruturas;

public class AtravessarTexto {

	public static void main(String[] args) {
		//checarValido("((())");
		checarValido("[(5 + x) – (y + z)]");
	}
	
	public static boolean checarValido(String texto) {
		Pilha minhaPilha = new Pilha(texto.length());
		
		for(int i=0;i<texto.length();i++) {
			char atual = texto.charAt(i);
			if(atual == '(' || atual == '[') {
				minhaPilha.push(atual);
			}
			if(atual == ')') {
				char abre = minhaPilha.pop();
				if(abre != '(') {
					System.err.println(texto+" NÃO É VÁLIDO, FECHOU ERRADO!");
					return false;
				}
			}
			if(atual == ']') {
				char abre = minhaPilha.pop();
				if(abre != '[') {
					System.err.println(texto+" NÃO É VÁLIDO, FECHOU ERRADO!");
					return false;
				}
			}
		}
		
		if(!minhaPilha.isEmpty()) {
			System.err.println(texto+" NÃO É VÁLIDO, FALTOU FECHAR!");
			return false;	
		}
		
		System.out.println(texto+" É VÁLIDO!");
		
		return true;
	}

}
