package br.erickweil.grafos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Random;

import br.erickweil.estruturas.ListaEncadeada;
import br.erickweil.graficos.WeilGraficos;
import br.erickweil.grafos.Grafo.GrafoVertice;
import br.erickweil.utilidades.Vector2;

public class DesenharGrafo<T> implements  WeilGraficos.Callback{
	int velocidadeSimulacao = 1;
	static int qual = 1;

	public static class PhysicalVertice<T> {
		static Random rdn = new Random();
		public T value;
		public Vector2 pos;
		
		public PhysicalVertice(Vector2 pos,T value) {
			this.value = value;
			this.pos = new Vector2(pos);
		}

		public PhysicalVertice(T value) {
			this.value = value;
			this.pos = new Vector2(rdn.nextFloat(), rdn.nextFloat());
		}
		
		@Override
		public String toString() {
			return ""+value;
		}
	}
	
	public Grafo<PhysicalVertice<T>> getPhysicalGraph(Grafo<T> graph) {
		var ret = new GrafoEncadeado<DesenharGrafo.PhysicalVertice<T>>();
		
		int i =0;
		for(GrafoVertice<T> v : graph) {
			var pv = ret.criarVertice(new PhysicalVertice<T>(v.getValor()));
			ret.getVertice(pv).setEstado(i);
			graph.getVertice(i).setEstado(i);
			i++;
		}
		
		for(GrafoVertice<T> v : graph) {
			for(GrafoVertice<T> conn : v) {
				ret.conectarDirecionado(v.getEstado(), conn.getEstado());
			}
		}		
		
		return ret;
	}
	
	public static void main(String[] args) {
		Grafo<PhysicalVertice<String>> grafo = null;

		Random rdn = new Random();

		if(qual == 0) {
		grafo = new GrafoEncadeado<>();
		var _0 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.35f, 0.3f),"0"));
		var _1 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.45f, 0.4f),"1"));
		var _2 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.2f, 0.75f),"2"));
		var _3 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.7f, 0.3f),"3"));
		var _4 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.5f, 0.67f),"4"));
		var _5 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.76f, 0.6f),"5"));
		var _6 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.55f, 0.9f),"6"));
		var _7 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.48f, 0.10f),"7"));
		var _8 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.15f, 0.5f),"8"));
		var _9 = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.33f, 0.1f),"9"));

		grafo.conectar(_0, _2);
		grafo.conectar(_0, _9);
		grafo.conectar(_0, _8);
		grafo.conectar(_1, _0);
		grafo.conectar(_1, _3);
		grafo.conectar(_1, _4);
		grafo.conectar(_2, _4);
		grafo.conectar(_2, _6);
		grafo.conectar(_2, _8);
		grafo.conectar(_3, _5);
		grafo.conectar(_3, _7);
		grafo.conectar(_4, _6);
		grafo.conectar(_8, _9);
		grafo.conectar(_7, _9);
		
		} else if(qual == 1) {
			grafo = new GrafoEncadeado<>();
			int w = 8;
			int h = 8;
			for(int y = 0; y < h; y++) {
				for(int x = 0; x < w; x++) {
					float px = (x/(1.0f*w) * 0.9f) + 0.1f;
					float py = (y/(1.0f*h) * 0.9f) + 0.1f;
					int index = y*w+x;
					var v = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(px, py),""+index));
				
					if(new Random().nextDouble() > 0.5) {
						if(y > 0) {
							grafo.conectar((y-1)*w+x, v);
						}
					}
					if(new Random().nextDouble() > 0.5) {
						if(x > 0) {
							grafo.conectar(y*w+(x-1), v);
						}
					}
					if(new Random().nextDouble() > 0.5) {
						if(y > 0 && x > 0) {
							grafo.conectar((y-1)*w+(x-1), v);
						}
					}
					
				}
			}
		} else if(qual == 2) {
			grafo = new GrafoEncadeado<>();
			var _A = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.5f, 0.8f),"A"));
			var _B = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.5f, 0.5f),"B"));
			var _C = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.5f, 0.15f),"C"));
			var _D = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.7f, 0.8f),"D"));
			var _E = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.8f, 0.15f),"E"));
			var _S = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.15f, 0.8f),"S"));
			var _G = grafo.criarVertice(new PhysicalVertice<String>(new Vector2(0.8f, 0.5f),"G"));
		
			grafo.conectarDirecionado(_A, _S);
			grafo.conectarDirecionado(_A, _D);
			grafo.conectarDirecionado(_A, _B);

			grafo.conectarDirecionado(_B, _S);
			grafo.conectarDirecionado(_B, _C);
			grafo.conectarDirecionado(_B, _A);

			grafo.conectarDirecionado(_C, _E);
			grafo.conectarDirecionado(_C, _B);

			grafo.conectarDirecionado(_D, _G);
			grafo.conectarDirecionado(_D, _A);

			grafo.conectarDirecionado(_E, _C);
			grafo.conectarDirecionado(_G, _D);

			
			grafo.conectarDirecionado(_S, _B);
			grafo.conectarDirecionado(_S, _A);
		}

		if(grafo != null) {
			WeilGraficos.iniciarGraficos(new DesenharGrafo<>(grafo),true);
		}
	}
	
	
	Grafo<PhysicalVertice<T>> physGraph;

	public DesenharGrafo(Grafo<PhysicalVertice<T>> physGraph) {
		this.physGraph = physGraph;
		this.physGraph.printar();
	}

	int canvasWidth = 0;
	int canvasHeight = 0;
	int canvasFrameCount = 0;
	@Override
	public void onDraw(Graphics2D g, int w, int h) {
		canvasFrameCount++;
		if(velocidadeSimulacao > 0 && (canvasFrameCount % velocidadeSimulacao == 0)) {
			if(objetivo != null && filaVisita != null) {
				// TODO Auto-generated method stub
				if(simulateVisitStep()) {
					achou = true;
				}
				
			}
		}

		var caminho = filaVisita == null || filaVisita.isEmpty() ? null : filaVisita.getFirst();

		canvasWidth = w;
		canvasHeight = h;
		// TODO Auto-generated method stub
		int size = 28;
		for(var v : physGraph) {			
			Vector2 pos = v.getValor().pos;
			int v_caminho = caminho == null ? 0 : caminho.indexOf(v);
			int x = (int)(pos.x * w);
			int y = (int)(pos.y * h);
			
			g.setStroke(new BasicStroke(3.0f));			
			for(var conn : v) {
				int conn_caminho = caminho == null ? 0 : caminho.indexOf(conn);
				int connx = (int)(conn.getValor().pos.x * w);
				int conny = (int)(conn.getValor().pos.y * h);

				if(v_caminho != -1 && conn_caminho != -1 && (v_caminho == conn_caminho-1 || v_caminho == conn_caminho+1))
					g.setColor(achou ? Color.GREEN : Color.RED);
				else
					g.setColor(Color.WHITE);

				g.drawLine(x, y, connx, conny);
			}			
		}

		for(var v : physGraph) {			
			Vector2 pos = v.getValor().pos;
			int x = (int)(pos.x * w);
			int y = (int)(pos.y * h);
			
			g.setStroke(new BasicStroke(3.0f));
			
			g.setColor(Color.BLACK);
			g.fillOval( x-size, y-size, size*2, size*2);

			if(v == objetivo)
				g.setColor(Color.GREEN);
			else if(caminho != null && caminho.getFirst() == v)
				g.setColor(Color.RED);
			else if(v.getEstado() == Grafo.EXPLORADO)
				g.setColor(Color.RED);
			else
				g.setColor(Color.WHITE);
			g.drawOval( x-size, y-size, size*2, size*2);
			
			//g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.PLAIN,size));
			WeilGraficos.drawCenteredString(g, ""+v.getValor(), new Rectangle(x-size, y-size, size*2, size*2));
			
		}
	}
	
	ListaEncadeada<ListaEncadeada<GrafoVertice<PhysicalVertice<T>>>> filaVisita;
	GrafoVertice<PhysicalVertice<T>> objetivo;
	boolean achou;
	boolean depthFirst;
	private boolean simulateVisitStep() {
		// A ideia é guardar no INÍCIO da fila o caminho de cada vertice expandido

		if(filaVisita.isEmpty()) return true;

		// Remove o primeiro caminho da fila.
		var caminho = filaVisita.removeFirst();
		// Pega o último nó desse caminho
		var ultimo = caminho.getLast();

		// Caminho encontrado
		if(ultimo == objetivo) {
			filaVisita.addFirst(caminho);
			ultimo.setEstado(Grafo.EXPLORADO);
			return true;
		} else if(ultimo.getEstado() != Grafo.EXPLORADO) {
			for(var conn : ultimo) {
				var novo_caminho = caminho.clone();
				novo_caminho.addLast(conn);

				if(depthFirst)
				filaVisita.addFirst(novo_caminho);
				else
				filaVisita.addLast(novo_caminho);
			}

			ultimo.setEstado(Grafo.EXPLORADO);
		}

		return false;
	}

	@Override
	public void onClick(int x, int y, int button) {

		if(achou) {
			filaVisita = null;
			objetivo = null;
			achou = false;
		}

		if(objetivo != null && filaVisita != null) {
			// TODO Auto-generated method stub

			if(velocidadeSimulacao > 0) return;

			if(simulateVisitStep()) {
				achou = true;
			}
			return;
		}

		int minDist = -1;
		GrafoVertice<PhysicalVertice<T>> minVert = null;
		for(var v : physGraph) {			
			Vector2 pos = v.getValor().pos;
			int vx = (int)(pos.x * canvasWidth);
			int vy = (int)(pos.y * canvasHeight);

			int dist = (vx - x)*(vx - x) + (vy - y)*(vy - y);
			if(minDist == -1 || dist < minDist) {
				minDist = dist;
				minVert = v;
			}

			v.setEstado(Grafo.INICIAL);
		}

		if(filaVisita == null) {
			var t = new ListaEncadeada<GrafoVertice<PhysicalVertice<T>>>();
			t.addFirst(minVert);
			filaVisita = new ListaEncadeada<>();
			filaVisita.addFirst(t);
			return;
		}

		if(objetivo == null) {
			objetivo = minVert;
			depthFirst = button == 1;
			return;
		}
		
	}
}
