package br.erickweil.estruturas;

import java.util.Iterator;

import javax.sound.sampled.LineUnavailableException;

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
	
	public static class Elemento<T> {
        T valor;
        Elemento<T> prox;

        public Elemento(T valor) {
            this.valor = valor;
            this.prox = null;
        }
    }

    private Elemento<T> inicio;
    private Elemento<T> fim;
    private int tamanho;

    public ListaEncadeada() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void addFirst(T valor) {
        Elemento<T> novo = new Elemento<>(valor);
        if(isEmpty()) {
            // Se estiver vazia, tanto inicio como fim é o novo
            inicio = novo;
            fim = novo;
        } else {
            // Faça o novo apontar para inicio, e então
            // o novo se torna o inicio
            novo.prox = inicio;
            inicio = novo;
        }
        tamanho++;
    }

    public void addLast(T valor) {
        Elemento<T> novo = new Elemento<>(valor);
        if(isEmpty()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.prox = novo;
            fim = novo;
        }
        tamanho++;
    }

    public T removeFirst() {
        T ret = inicio.valor;
        if(size() == 1) {
            clear();
        } else {
            inicio = inicio.prox;
            tamanho--;
        }
        return ret;
    }

    public T remove(int indice) {
        if(indice == 0) {
            return removeFirst();
        } else {
            Elemento<T> anterior = getElemento(indice-1);
            Elemento<T> atual = anterior.prox;

            anterior.prox = atual.prox;

            if(atual == fim) {
                fim = anterior;
            }

            tamanho--;
            return atual.valor;
        }
    }

    public T removeLast() {
        return remove(tamanho-1);
    }

    public T get(int indice) {
        return getElemento(indice).valor;
    }

    public T set(int indice, T valor) {
        Elemento<T> elem = getElemento(indice);
        T ret = elem.valor;
        elem.valor = valor;

        return ret;
    }



    private Elemento<T> getElemento(int indice) {
        Elemento<T> atual = inicio;
        int contador = 0;
        while(atual != null) {
            if(contador == indice) {
                return atual;
            }
            contador++;
            atual = atual.prox;
        }
        System.err.println("Não encontrado");
        return null;
    }

    public boolean contains(T valor) {
        Elemento<T> atual = inicio;
        while(atual != null) {
            if(atual.valor.equals(valor))  {
                return true;
            }
            atual = atual.prox;
        }
        return false;
    }

    public int indexOf(T valor) {
        Elemento<T> atual = inicio;
        int contador = 0;
        while(atual != null) {
            if(atual.valor.equals(valor))  {
                return contador;
            }
            contador++;
            atual = atual.prox;
        }
        return -1;
    }

    public int lastIndexOf(T valor) {
        Elemento<T> atual = inicio;
        int contador = 0;
        int pos = -1;
        while(atual != null) {
            if(atual.valor.equals(valor))  {
                pos = contador;
            }
            contador++;
            atual = atual.prox;
        }
        return pos;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public T getFirst() {
        return inicio.valor;
    }

    public T getLast() {
        return fim.valor;
    }

    public int size() {
        return tamanho;
    }

    public void clear() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

	public ListaEncadeada<T> clone() {
		ListaEncadeada<T> ret = new ListaEncadeada<T>();
		for(T elem : this) {
			ret.addLast(elem);
		}
		return ret;
	}

    public void printar() {
        Elemento<T> atual = inicio;
        System.out.println("tamanho:"+tamanho);
        while(atual != null) {
            System.out.print(atual.valor);
            if(atual == inicio) {
                System.out.println(" <-- inicio");
            } else if(atual == fim) {
                System.out.println(" <-- fim");
            } else {
                System.out.println();
            }
            atual = atual.prox;
        }
    }

	/**
	 * Método principal. Para testar o código e ver
	 * se os métodos funcionam como deveriam.
	 */
	public static void main(String[] args) {
		System.out.println("OK");
		
		// Criando uma lista inicialmente vazia.
		ListaEncadeada<String> lista = new ListaEncadeada<>();

		// Testando o método addFirst
		lista.addLast("Batata");
		lista.addFirst("Cenoura");
		lista.addFirst("Melancia");
		lista.addLast("Uva");
	
		// Se printar na tela esses três valores é porque adicionou
		System.out.println("Lista antes de remover: ");
		lista.printar();
		
		//System.out.println(lista.get(2));
		
		// Testando o método removeFirst
		String removido = lista.removeFirst();
		System.out.println("\nRemovido: "+removido);
		
		// Se printar na tela a lista sem o que foi removido é porque removeu
		System.out.println("Lista depois de remover: ");
		lista.printar();
	}

}
