package br.erickweil.estruturas;

public class ListaEncadeada {

    public static class Elemento {
        String valor;
        Elemento prox;

        public Elemento(String valor) {
            this.valor = valor;
            this.prox = null;
        }
    }

    private Elemento inicio;
    private Elemento fim;
    private int tamanho;

    public ListaEncadeada() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void addFirst(String valor) {
        Elemento novo = new Elemento(valor);
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

    public void addLast(String valor) {
        Elemento novo = new Elemento(valor);
        if(isEmpty()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.prox = novo;
            fim = novo;
        }
        tamanho++;
    }

    public String removeFirst() {
        String ret = inicio.valor;
        if(size() == 1) {
            clear();
        } else {
            inicio = inicio.prox;
            tamanho--;
        }
        return ret;
    }

    public String get(int indice) {
        return getElemento(indice).valor;
    }

    public String set(int indice, String valor) {
        Elemento elem = getElemento(indice);
        String ret = elem.valor;
        elem.valor = valor;

        return ret;
    }

    public String remove(int indice) {
        if(indice == 0) {
            return removeFirst();
        } else {
            Elemento anterior = getElemento(indice-1);
            Elemento atual = anterior.prox;

            anterior.prox = atual.prox;

            return atual.valor;
        }
    }

    private Elemento getElemento(int indice) {
        Elemento atual = inicio;
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

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public String getFirst() {
        return inicio.valor;
    }

    public String getLast() {
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

    public void printar() {
        Elemento atual = inicio;
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

    public static void main(String[] args) {
        ListaEncadeada lista = new ListaEncadeada();

        lista.addLast("A");
        lista.addLast("B");
        lista.addLast("C");

        lista.addLast("D");
        lista.addLast("E");
        lista.addLast("F");

        lista.printar();

        System.out.println("Get 2:"+lista.get(-1));

    }
}
