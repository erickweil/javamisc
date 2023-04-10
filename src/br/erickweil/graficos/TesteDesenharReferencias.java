package br.erickweil.graficos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class TesteDesenharReferencias {

	public int[] notas;
	public double media;
	public String mensagem;
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		TesteDesenharReferencias ret = new TesteDesenharReferencias();
		
		ret.notas = new int[] { 50, 60, 70, 90};
		ret.media = (ret.notas[0] + ret.notas[1] + ret.notas[2] + ret.notas[3]) / 4.0;
		
		ret.mensagem = ret.media >= 60.0 ? "Passou" : "Reprovou";
		
		DesenharReferenciasObjeto.verObjeto(ret);
	}

}
