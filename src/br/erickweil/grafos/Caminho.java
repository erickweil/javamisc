package br.erickweil.grafos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.erickweil.estruturas.ListaEncadeada;
import br.erickweil.graficos.WeilGraficos;

public class Caminho implements WeilGraficos.Callback {
	
	int velocidadeSimulacao = 1;
	public static void main(String[] args) {
		WeilGraficos.iniciarGraficos(new Caminho(10,10),true);
	}

	public int colunas;
	public int linhas;
	public int quadw;
	public int[] grade;
	public int posx;
	public int posy;
	public int objetivox=5;
	public int objetivoy=5;
	public List<Integer> caminho;
	public Caminho(int linhas, int colunas) {
		this.colunas = colunas;
		this.linhas = linhas;
		this.grade = new int[colunas * linhas];
		this.caminho = new ArrayList<>();
		
		visitados = new HashSet<>();

		Random rdn = new Random(0xCAFEBABE);
		for(int y=0;y<linhas;y++) {
			for(int x=0;x<colunas;x++) {
				int indice = y*colunas + x;
				grade[indice] = rdn.nextInt(10) > 7 ? 1 : 0;
			}
		}
	}

	int canvasFrameCount = 0;
	@Override
	public void onDraw(Graphics2D g, int w, int h) {
		quadw = w / colunas;
		if(h / linhas < quadw) {
			quadw = h / linhas;
		}
		int quadw2 = quadw /2;
		int quadw4 = quadw /4;

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, quadw * colunas, quadw * linhas);

		for(int y=0;y<linhas;y++) {
			for(int x=0;x<colunas;x++) {
				int indice = y*colunas + x;
				int quadx = x * quadw;
				int quady = y * quadw;
				
				if(visitados.contains(indice)) {
					g.setColor(Color.GRAY);
				} else {
					g.setColor(Color.BLACK);
				}

				if(grade[indice] == 0)
				{	
					g.fillRect(quadx+1, quady+1, quadw-2, quadw-2);
				}
			}
		}

		g.setColor(Color.GREEN);
		g.fillRect(posx*quadw+quadw4, posy*quadw+quadw4, quadw2, quadw2);

		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(3.0f));
		int ox = objetivox*quadw+quadw2;
		int oy = objetivoy*quadw+quadw2;
		g.drawLine(ox-quadw4,oy-quadw4,ox+quadw4,oy+quadw4);
		g.drawLine(ox-quadw4,oy+quadw4,ox+quadw4,oy-quadw4);

		canvasFrameCount++;
		if(velocidadeSimulacao > 0 && (canvasFrameCount % velocidadeSimulacao == 0)) {
			if(objetivo != -1 && filaVisita != null) {
				// TODO Auto-generated method stub
				if(simulateVisitStep()) {
					achou = true;
				}
			}
		}

		if(filaVisita != null && !filaVisita.isEmpty()) {
		var caminho = filaVisita.getFirst();
		for(int v : caminho) {	
			int vx = v % colunas;
			int vy = v / colunas;
			int quadx = vx * quadw;
			int quady = vy * quadw;
			
			g.setColor(Color.GREEN);
			g.fillRect(quadx+1, quady+1, quadw-2, quadw-2);
		}
		}
	}

	ListaEncadeada<ListaEncadeada<Integer>> filaVisita;
	Set<Integer> visitados;
	int[] direcoes = {0,-1, 1,0, 0,1, -1,0};
	int objetivo;
	boolean achou = false;
	boolean depthFirst = false;
	private boolean simulateVisitStep() {
		// A ideia é guardar no INÍCIO da fila o caminho de cada vertice expandido

		if(filaVisita.isEmpty()) return false;
		// Remove o primeiro caminho da fila.
		var caminho = filaVisita.removeFirst();
		// Pega o último nó desse caminho
		var ultimo = caminho.getLast();
		int ultimox = ultimo % colunas;
		int ultimoy = ultimo / colunas;

		// Caminho encontrado
		if(ultimo == objetivo) {
			filaVisita.addFirst(caminho);
			visitados.add(ultimo);
			return true;
		} else if(!visitados.contains(ultimo)) {
			for(int i = 0; i < 4; i++) {
				int connx = ultimox + direcoes[i*2+0];
				int conny = ultimoy + direcoes[i*2+1];
				if(connx < 0 || connx >= colunas || conny < 0 || conny >= linhas) continue;
				if(grade[conny*colunas + connx] != 0) continue;

				var novo_caminho = caminho.clone();
				novo_caminho.addLast(conny*colunas + connx);

				if(depthFirst)
				filaVisita.addFirst(novo_caminho);
				else
				filaVisita.addLast(novo_caminho);
			}

			visitados.add(ultimo);
		}

		return false;
	}

	@Override
	public void onClick(int x, int y, int button) {
		int qx = x / quadw;
		int qy = y / quadw;
		if(qx < 0 || qx >= colunas || qy < 0 || qy >= linhas) return;
		
		//System.out.printf("quadw:%d, Clique [%d,%d] --> {%d,%d}\n",quadw,x,y,qx,qy);
		int indice = qy*colunas + qx;

		if(button == 1) {
			grade[indice] = grade[indice] == 0 ? 1 : 0;
		} else {
			objetivox = qx;
			objetivoy = qy;
			objetivo = indice;

			visitados = new HashSet<>();
			var t = new ListaEncadeada<Integer>();
			t.addFirst(posy*colunas + posx);
			filaVisita = new ListaEncadeada<>();
			filaVisita.addFirst(t);

			achou = false;
			depthFirst = false;
		}
	}
}
