package br.erickweil.arvores;

import java.io.IOException;

import br.erickweil.utilidades.LerArquivo;
/*
 A ideia é guardar palavras de forma que cada letra ocupa um nó, 
 E o caminho que você seguir vai construir a palavra
   |
   \-a
   | \--r
   \-c
   | \--a
   | |	\--l
   | |	\--m
   | |	   \--p
   | |	      \--o
   | \--o
   |    \--m
   | 	   \--e
   | 	      \--ç
   | 	         \--o
   \-t
	 \--e
	 	\--r
	 
 Palavras: ar, calmo, campo, começo, ter
 
 
*/
public class Trie {
	public static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz0123456789";
	public static final int N_ALFABETO = ALFABETO.length();
	public Trie[] filhos;
	public boolean fim;
	
	public Trie() {
		this.filhos = new Trie[N_ALFABETO];
		this.fim = false;
	}
	
	/**
	 * Posição do caractere no ALFABETO, ou -1 se ele não faz parte do alfabeto.
	 * Letras maiúsculas são tratadas como minúsculas.
	 */
	private static int indice(char c) {
		return ALFABETO.indexOf(Character.toLowerCase(c));
	}

	/**
	 * Insere a palavra na Trie. Palavras com caracteres fora do ALFABETO são ignoradas.
	 * @return verdadeiro se a palavra foi inserida
	 */
	public static boolean insere(Trie raiz, String chave) {
		for(int nivel = 0; nivel < chave.length(); nivel++) {
			if(indice(chave.charAt(nivel)) == -1) return false;
		}

		Trie p = raiz;
		for(int nivel = 0; nivel < chave.length(); nivel++) {
			int i = indice(chave.charAt(nivel));
			if(p.filhos[i] == null) {
				p.filhos[i] = new Trie();
			}
			p = p.filhos[i];
		}
		p.fim = true;
		return true;
	}

	public static boolean busca(Trie raiz, String chave) {
		Trie p = raiz;
		for(int nivel = 0; nivel < chave.length(); nivel++) {
			int i = indice(chave.charAt(nivel));
			if(i == -1 || p.filhos[i] == null) {
				return false;
			}
			p = p.filhos[i];
		}
		return p.fim;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		final Trie raiz = new Trie();
		
		
		
		LerArquivo.linhaPorLinha("tags.txt",linha -> {
			if(linha.length() <= 6)
			insere(raiz,linha);
			
			return false;
		});
	
		System.out.println("\nTestando:");
		System.out.println("mylove? "+busca(raiz, "mylove"));
		System.out.println("123456? "+busca(raiz, "123456"));
		System.out.println("super? "+busca(raiz, "super"));
		System.out.println("amor? "+busca(raiz, "amor"));
		System.out.println("fghrff? "+busca(raiz, "fghrff"));
		
		
		
		long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		System.out.println("Memória usada:"+used);
	}
	

}
