package br.erickweil.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import br.erickweil.arvores.Arvore;
import br.erickweil.ordenacao.InsertionSort;
import br.erickweil.utilidades.WeilArrays;

public class DesenharArvore implements WeilGraficos.Callback{

	public static void main(String[] args) {		
		WeilGraficos.iniciarGraficos(new DesenharArvore(null));
	}
	
	public static Arvore gerarNovaArvore() {
		int n = new Random().nextInt(50);
		//int n = 80 + new Random().nextInt(5);
		int arr[] = new int[n];
		WeilArrays.iniciarAleatorioUnico(arr, 100);
		//new InsertionSort().ordenar(arr);
		
		Arvore raiz = new Arvore(arr[0],null,null);
		for(int i=1;i<arr.length;i++)
			raiz.adicionarElemento(arr[i]);
		
		return raiz;
		//return Arvore.doArrayOrdenado(arr);
	}
	
	Arvore raiz;
	int size = 20;
	public DesenharArvore(Arvore raiz) {
		this.raiz = raiz;
		
		if(this.raiz == null) this.raiz = gerarNovaArvore();
	}

	@Override
	public void onDraw(Graphics2D g, int w, int h) {
		desenhar(g,raiz,0, 0);
	}

	private int last_x;
	private int last_y;
	public int desenhar(Graphics2D g, Arvore galho, int nivel, int prevOff) {
		if(galho == null) return size;
		
		int startoff = desenhar(g,galho.esquerda,nivel+1,prevOff);
		int esquerda_x = last_x;
		int esquerda_y = last_y;
		
		String txt = ""+galho.valor;
		int x = prevOff + startoff;
		int y = (nivel * (size*2)) + size;
		
		int endoff = desenhar(g,galho.direita,nivel+1,prevOff + startoff + size);
		int direita_x = last_x;
		int direita_y = last_y;
		
		g.setStroke(new BasicStroke(3.0f));
		g.setColor(Color.RED);
		if(galho.direita != null)
		g.drawLine(x, y, direita_x, direita_y -size);
		if(galho.esquerda != null)
		g.drawLine(x, y, esquerda_x, esquerda_y -size);
		
		g.setColor(Color.BLACK);
		g.fillOval( x-size, y-size, size*2, size*2);
		g.setColor(Color.RED);
		g.drawOval( x-size, y-size, size*2, size*2);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,size));
		WeilGraficos.drawCenteredString(g, txt, new Rectangle(x-size, y-size, size*2, size*2));
		
		last_x = x;
		last_y = y;
		return startoff + endoff + size; 
	}

	@Override
	public void onClick(int x, int y, int button) {
		System.out.println("Clicou "+x+","+y);
		this.raiz =  gerarNovaArvore();
	}
}
