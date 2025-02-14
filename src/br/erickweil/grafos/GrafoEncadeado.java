package br.erickweil.grafos;

import java.util.Iterator;

import br.erickweil.estruturas.ListaEncadeada;

public class GrafoEncadeado<T> extends Grafo<T> {	
	public static class Vertice<T> extends GrafoVertice<T> {
		public int estado;
		public T valor;
		public ListaEncadeada<GrafoVertice<T>> conexoes;
		
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

        @Override
        public Iterator<GrafoVertice<T>> iterator() {
            return conexoes.iterator();
        }

        @Override
        public T getValor() {
            return valor;
        }

        @Override
        public int getEstado() {
            return estado;
        }

        @Override
        public void setEstado(int estado) {
            this.estado = estado;
        }

        @Override
        public void addConexao(GrafoVertice<T> v) {
            conexoes.addLast(v);
        }
	}
	
	public ListaEncadeada<GrafoVertice<T>> vertices;
	
	public GrafoEncadeado() {
		this.vertices = new ListaEncadeada<GrafoVertice<T>>();
	}
	
	public int criarVertice(T valor) {
		Vertice<T> v = new Vertice<T>(valor);
		vertices.addLast(v);
		return vertices.size()-1;
	}
	
	public void conectar(int a,int b) {
        GrafoVertice<T> va = vertices.get(a);
        GrafoVertice<T> vb = vertices.get(b);
        
		va.addConexao(vb);
		vb.addConexao(va);
	}
	
	public void conectarDirecionado(int a,int b) {
        GrafoVertice<T> va = vertices.get(a);
        GrafoVertice<T> vb = vertices.get(b);
		va.addConexao(vb);
	}

    @Override
	public Iterator<Grafo.GrafoVertice<T>> iterator() {
		return vertices.iterator();
	}

    @Override
    public GrafoVertice<T> getVertice(int a) {
        return vertices.get(a);
    }
}
