package br.erickweil.arvores;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import br.erickweil.graficos.DesenharReferenciasObjeto;
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
	
	public static void insere(Trie raiz, String chave) {
		byte[] chaveArr = chave.getBytes(StandardCharsets.UTF_8);
		int chaveLen = chaveArr.length;
		
		Trie p = raiz;
		for(int nivel = 0; nivel < chaveLen; nivel++) {
			int i = (chaveArr[nivel] & 0xFF) % N_ALFABETO;
			if(p.filhos[i] == null) {
				p.filhos[i] = new Trie();
			}
			p = p.filhos[i];
		}
		p.fim = true;
	}
	
	public static boolean busca(Trie raiz, String chave) {
		byte[] chaveArr = chave.getBytes(StandardCharsets.UTF_8);
		int chaveLen = chaveArr.length;
		
		Trie p = raiz;
		for(int nivel = 0; nivel < chaveLen; nivel++) {
			int i = (chaveArr[nivel] & 0xFF) % N_ALFABETO;
			if(p.filhos[i] == null) {
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
		
		
		
		long total = Runtime.getRuntime().totalMemory();
		long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		System.out.println("Memória usada:"+used);
	}
	

}
