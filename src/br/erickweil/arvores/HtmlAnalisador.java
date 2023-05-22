package br.erickweil.arvores;

public class HtmlAnalisador {

	private static void printar(ArvoreN<TagHtml> tag,String tabs) {
		System.out.print(tabs);
		System.out.println(tag.chave.tagPrint(false));
		
		System.out.print(tabs+"  ");
		System.out.println(tag.chave.texto);
		
		ArvoreN<TagHtml> f = tag.primeiroFilho;
		while(f != null) {
			printar(f,tabs+"  ");
			f = f.proximoIrmao;
		}
		
		System.out.print(tabs);
		System.out.println(tag.chave.tagPrint(true));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArvoreN<TagHtml> doc = new ArvoreN<TagHtml>(new TagHtml("html", ""));
		
		ArvoreN<TagHtml> head = doc.adicionarFilho(new TagHtml("head",""));
		
		head.adicionarFilho(new TagHtml("title", "Teste de Página"));
		
		ArvoreN<TagHtml> body = doc.adicionarFilho(new TagHtml("body",""));
		
		ArvoreN<TagHtml> div = body.adicionarFilho(new TagHtml("div", ""));
		
		div.adicionarFilho(new TagHtml("h1", "Olá"));
		div.adicionarFilho(new TagHtml("p", "Seja bem vindo, teste de página"));
		
		printar(doc,"");
	}

}
