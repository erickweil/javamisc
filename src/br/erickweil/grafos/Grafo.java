package br.erickweil.grafos;

import br.erickweil.estruturas.ListaEncadeada;

public class Grafo<T> {
	public static final int INICIAL = 0;
	public static final int EXPLORADO = 1;
	
	public static class Vertice<T> {
		public int estado;
		public T valor;
		public ListaEncadeada<Vertice<T>> conexoes;
		
		public Vertice(T valor) {
			this.estado = INICIAL;
			this.valor = valor;
			this.conexoes = new ListaEncadeada<>();
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return ""+valor;
		}
	}
	
	public ListaEncadeada<Vertice<T>> vertices;
	
	public Grafo() {
		this.vertices = new ListaEncadeada<Grafo.Vertice<T>>();
	}
	
	public Vertice<T> criarVertice(T valor) {
		Vertice<T> v = new Vertice<T>(valor);
		vertices.addLast(v);
		return v;
	}
	
	public void conectar(Vertice<T> a,Vertice<T> b) {
		a.conexoes.addLast(b);
		b.conexoes.addLast(a);
	}
	
	public void conectarDirecionado(Vertice<T> a,Vertice<T> b) {
		a.conexoes.addLast(b);
	}
	
	public void printar() {
		int nVertices = 0;
		int nConexoes = 0;
		for(Vertice<T> vert : this.vertices) {
			nVertices++;
			System.out.print(vert);
			System.out.print(": ");
			for(Vertice<T> conn : vert.conexoes) {
				nConexoes++;
				System.out.print(conn);
				System.out.print(", ");
			}
			System.out.println();
		}
		System.out.println("Vertices:"+nVertices+" Conexoes:"+nConexoes);
	}
	
	public void depthFirstSearch(Vertice<T> inicio, Vertice<T> objetivo) {		
		for(Vertice<T> v : this.vertices) {
			v.estado = INICIAL;
		}
		
		depthFirstVisit(inicio, objetivo);
	}
	
	private boolean depthFirstVisit(Vertice<T> v, Vertice<T> objetivo) {
		v.estado = EXPLORADO;
		System.out.println(v);
		
		if(v == objetivo) return true;
		
		if(v.conexoes.isEmpty()) return false;
		
		for(Vertice<T> conn : v.conexoes) {
			if(conn.estado == INICIAL)
			{
				if(depthFirstVisit(conn, objetivo)) return true;
			}
		}
		return false;
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
		
		graph.depthFirstSearch(v3, v0);
	}
}
