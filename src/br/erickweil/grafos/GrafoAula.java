package br.erickweil.grafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class GrafoAula {
    public static class Grafo {
        List<Vertice> vertices;   

        public Grafo() {
            this.vertices = new ArrayList<>();
        }

        public Vertice addVertice(String nome) {
            Vertice novo = new Vertice(nome);
            vertices.add(novo);
            return novo;
        }

        public void conectar(Vertice a, Vertice b) {
            a.conexoes.add(b);
        }

        public void conectarDuplo(Vertice a, Vertice b) {
            a.conexoes.add(b);
            b.conexoes.add(a);
        }

        public void printar() {
            System.out.println("Nº de vértices:"+vertices.size());
            for(int i = 0;i < vertices.size(); i++) {
                Vertice v = vertices.get(i);
                System.out.print(v.nome+": ");
                for(Vertice conn : v.conexoes) {
                    System.out.print(conn.nome+", ");
                }
                System.out.println();
            }
        }

        /* Busca por Profundidade
            Marcar que foi visitado
            Verificar se é igual ao objetivo, e retornar que a encontrou se for
            Para todas as conexões do vértice:
                Se a conexão não foi visitado:
                    Busca por profundidade na conexão
                    se a busca retornar que encontrou, retornar que encontrou
                    senão continua
            Se chegou aqui retorna que não encontrou
        */
        public boolean buscaProfundidade(Vertice atual, Vertice objetivo, Set<Vertice> visitados) {
            visitados.add(atual);

            if(atual == objetivo) {
                System.out.println(atual.nome);
                return true;
            }

            for(Vertice conexao : atual.conexoes) {
                if(visitados.contains(conexao)) continue;

                boolean achou = buscaProfundidade(conexao, objetivo, visitados);

                if(achou) {
                    System.out.println(atual.nome);
                    return true;
                }
            }
            return false;
        }

        // Busca em Largura
        // A ideia é adicionar novos caminhos à fila
        public List<Vertice> buscaEmLargura(Vertice inicio, Vertice objetivo) {
            Set<Vertice> visitados = new HashSet<>();
            LinkedList<LinkedList<Vertice>> caminhos = new LinkedList<>();

            LinkedList<Vertice> caminho = new LinkedList<>();
            caminho.add(inicio);

            caminhos.add(caminho);
            // Remove o primeiro caminho e expande as conexões re-inserindo
            // os novos caminhos;
            while(!caminhos.isEmpty()) {
                caminho = caminhos.removeFirst(); // Remove o primeiro
                Vertice ultimo = caminho.getLast();

                if(ultimo == objetivo) {
                    return caminho;
                }

                visitados.add(ultimo);
                for(Vertice conn : ultimo.conexoes) {
                    if(visitados.contains(conn)) continue;
                    LinkedList<Vertice> novoCaminho = (LinkedList<Vertice>)caminho.clone();
                    novoCaminho.add(conn);
                    caminhos.addLast(novoCaminho);
                }
            }

            return null;
        }
    }

    public static class Vertice {
        String nome;
        Set<Vertice> conexoes;
        
        public Vertice(String nome) {
            this.nome = nome;
            this.conexoes = new HashSet<>();
        }
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        Vertice S = grafo.addVertice("S");
        Vertice A = grafo.addVertice("A");
        Vertice B = grafo.addVertice("B");
        Vertice C = grafo.addVertice("C");
        Vertice D = grafo.addVertice("D");
        Vertice E = grafo.addVertice("E");
        Vertice G = grafo.addVertice("G");

        grafo.conectarDuplo(S, A);
        grafo.conectarDuplo(S, B);

        grafo.conectarDuplo(A, B);
        grafo.conectarDuplo(A, D);

        grafo.conectarDuplo(B, C);
        grafo.conectarDuplo(C, E);
        grafo.conectarDuplo(D, G);

        grafo.printar();

        System.out.println("Encontrando caminho de S até G");
        List<Vertice> caminho = grafo.buscaEmLargura(E, S);
        for(Vertice v : caminho) {
            System.out.println(v.nome);
        }
        //boolean achou = grafo.buscaProfundidade(S,E,new HashSet<Vertice>());
        //System.out.println(achou ? "Achou" : "Não Achou");
    }
}
