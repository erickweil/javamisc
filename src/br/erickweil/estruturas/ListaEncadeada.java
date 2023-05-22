package br.erickweil.estruturas;

import java.util.Iterator;

/**
 * Classe ListaEncadeada
 * Esta classe permite construir uma lista onde que cada
 * elemento aponta para o próximo, até chegar no fim com um
 * elemento que aponta para 'null'
 */
public class ListaEncadeada<T> implements Iterable<T> {
	
	@Override
	public Iterator<T> iterator() {
		final ListaEncadeada<T> that = this;
		return new Iterator<T>() {
			Elemento<T> atual = that.inicio;
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return atual != null;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				T valor = atual.valor;
				atual = atual.prox;
				return valor;
			}
		};
	}
	
	/**
	 * Classe que define um elemento na lista
	 * Contém o valor e uma referência ao próximo
	 */
	public static class Elemento<T> {
		T valor;
		Elemento<T> prox;

		public Elemento(T valor) {
			this.valor = valor;
			this.prox = null;
		}

		/** Para escrever na tela os elementos
		* Ex: A --> B --> C --> null */
		@Override
		public String toString() {
			return this.valor + " --> " + this.prox;
		}
	}
	
	// ------- Atributos da classe ListaEncadeada -------
	// indica o início da lista.
	Elemento<T> inicio;

	/**
	 * Este método adiciona um elemento no início da lista
	 * Ou seja, este novo valor será o primeiro da lista
	 * @param valor O valor que vai será adicionado
	*/
	public void addFirst(T valor) {
		Elemento<T> novo = new Elemento<T>(valor);
		novo.prox = inicio;
		inicio = novo;
	}

	/**
	 * Remove um elemento do início da lista
	 * @return o valor removido
	 */
	public T removeFirst() {
		T valor = inicio.valor;
		inicio = inicio.prox;
		return valor;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return inicio == null;
	}

	/**
	 * Método principal. Para testar o código e ver
	 * se os métodos funcionam como deveriam.
	 */
	public static void main(String[] args) {
		System.out.println("OK");
		
		// Criando uma lista inicialmente vazia.
		ListaEncadeada<String> lista = new ListaEncadeada<String>();

		// Testando o método addFirst
		lista.addFirst("Batata");
		lista.addFirst("Cenoura");
		lista.addFirst("Melancia");
	
		// Se printar na tela esses três valores é porque adicionou
		System.out.println("Lista antes de remover: " + lista.inicio);
		
		// Testando o método removeFirst
		String removido = lista.removeFirst();
		System.out.println("Removido: "+removido);
		
		// Se printar na tela a lista sem o que foi removido é porque removeu
		System.out.println("Lista depois de remover: " + lista.inicio);
	}

}