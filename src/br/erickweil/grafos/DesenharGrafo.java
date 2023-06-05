package br.erickweil.grafos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import br.erickweil.graficos.WeilGraficos;
import br.erickweil.grafos.Grafo.Vertice;
import br.erickweil.utilidades.Vector2;

public class DesenharGrafo<T> implements  WeilGraficos.Callback{
	
	public static class PhysicalVertice<T> {
		static Random rdn = new Random();
		public T value;
		public Vector2 pos;
		
		public PhysicalVertice(T value) {
			this.value = value;
			this.pos = new Vector2(rdn.nextFloat(), rdn.nextFloat());
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return ""+value;
		}
	}
	
	public Grafo<PhysicalVertice<T>> getPhysicalGraph(Grafo<T> graph) {
		var ret = new Grafo<DesenharGrafo.PhysicalVertice<T>>();
		
		int i =0;
		for(Vertice<T> v : graph.vertices) {
			var pv = ret.criarVertice(new PhysicalVertice<T>(v.valor));
			pv.estado = i;
			v.estado = i;
			i++;
		}
		
		for(Vertice<T> v : graph.vertices) {
			for(Vertice<T> conn : v.conexoes) {
				var pv = ret.vertices.get(v.estado);
				var pconn = ret.vertices.get(conn.estado);
				ret.conectarDirecionado(pv, pconn);
			}
		}
		
		
		return ret;
	}
	
	public static void main(String[] args) {
		Grafo<String> graph = new Grafo<>();
		Vertice<String> v0 = graph.criarVertice("v0");
		Vertice<String> v1 = graph.criarVertice("v1");
		Vertice<String> v2 = graph.criarVertice("v2");
		Vertice<String> v3 = graph.criarVertice("v3");
		Vertice<String> v4 = graph.criarVertice("v4");
		Vertice<String> v5 = graph.criarVertice("v5");
		
		graph.conectar(v0, v1);
		graph.conectar(v0, v2);
		graph.conectar(v1, v2);
		graph.conectar(v2, v5);
		graph.conectar(v3, v4);
		graph.conectar(v4, v2);
		
		graph.printar();
		
		WeilGraficos.iniciarGraficos(new DesenharGrafo(graph));
	}
	
	
	Grafo<PhysicalVertice<T>> physGraph;
	
	public DesenharGrafo(Grafo<T> graph) {
		physGraph = getPhysicalGraph(graph);
		physGraph.printar();
	}

	@Override
	public void onDraw(Graphics2D g, int w, int h) {
		// TODO Auto-generated method stub
		int size = 40;
		for(var v : physGraph.vertices) {
			simulate(v);
			
			Vector2 pos = v.valor.pos;
			int x = (int)(pos.x * w);
			int y = (int)(pos.y * h);
			
			g.setStroke(new BasicStroke(3.0f));
			
			for(var conn : v.conexoes) {
				int connx = (int)(conn.valor.pos.x * w);
				int conny = (int)(conn.valor.pos.y * h);
				g.drawLine(x, y, connx, conny);
				
			}
			
			g.setColor(Color.BLACK);
			g.fillOval( x-size, y-size, size*2, size*2);
			g.setColor(Color.RED);
			g.drawOval( x-size, y-size, size*2, size*2);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.PLAIN,size));
			WeilGraficos.drawCenteredString(g, ""+v.valor, new Rectangle(x-size, y-size, size*2, size*2));
			
		}
	}
	
	public void simulate(Vertice<PhysicalVertice<T>> v) {
		
		//for(var conn : v.conexoes) {
		//}
	}

	@Override
	public void onClick(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

}
