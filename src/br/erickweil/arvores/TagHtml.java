package br.erickweil.arvores;

public class TagHtml {
	String nome; // nome da TAG
	String texto; // Conteúdo de texto diretamente dentro dessa TAG
	
	public TagHtml(String nome,String texto) {
		this.nome = nome;
		this.texto = texto;
	}
	
	public String tagPrint(boolean fechando) {
		if(fechando)
		return String.format("<%s/>",nome);
		else
		return String.format("<%s>",nome);
	}
}
