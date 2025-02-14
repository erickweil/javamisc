package br.erickweil.grafos;

public abstract class Grafo<T> implements Iterable<Grafo.GrafoVertice<T>> {

	public static abstract class GrafoVertice<K> implements Iterable<GrafoVertice<K>> {
		public abstract K getValor();
		public abstract int getEstado();
		public abstract void setEstado(int estado);

		public abstract void addConexao(GrafoVertice<K> v);
	}

	public static final int INICIAL = 0;
	public static final int EXPLORADO = 1;

	abstract public int criarVertice(T valor);
	abstract public void conectar(int a,int b);
	abstract public void conectarDirecionado(int a,int b);
	abstract public GrafoVertice<T> getVertice(int a);
	
	public void printar() {
		int nVertices = 0;
		int nConexoes = 0;
		for(GrafoVertice<T> vert : this) {
			nVertices++;
			System.out.print(vert);
			System.out.print(": ");
			for(GrafoVertice<T> conn : vert) {
				nConexoes++;
				System.out.print(conn);
				System.out.print(", ");
			}
			System.out.println();
		}
		System.out.println("Vertices:"+nVertices+" Conexoes:"+nConexoes);
	}
	
	public void depthFirstSearch(int inicio, int objetivo) {		
		for(GrafoVertice<T> v : this) {
			v.setEstado(INICIAL);
		}
		
		depthFirstVisit(getVertice(inicio), getVertice(objetivo));
	}
	
	private boolean depthFirstVisit(GrafoVertice<T> vert, GrafoVertice<T> objetivo) {
		vert.setEstado(EXPLORADO);
		//System.out.println(vert);
		
		if(vert == objetivo) return true;

		for(GrafoVertice<T> conn : vert) {
			if(conn.getEstado() == INICIAL)
			{
				if(depthFirstVisit(conn, objetivo)) return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Grafo<String> graph = new GrafoEncadeado<>();
		int v0 = graph.criarVertice("v0");
		int v1 = graph.criarVertice("v1");
		int v2 = graph.criarVertice("v2");
		int v3 = graph.criarVertice("v3");
		int v4 = graph.criarVertice("v4");
		int v5 = graph.criarVertice("v5");
		
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
