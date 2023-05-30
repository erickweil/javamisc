package br.erickweil.grafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TesteGrafos {
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

        Vertice quintal = grafo.addVertice(
            "Quintal"
        );
        Vertice casinhacachorro = grafo.addVertice(
            "Cachorro"
        );
        Vertice cozinha = grafo.addVertice(
            "Cozinha"
        );
        Vertice sala = grafo.addVertice(
            "Sala"
        );
        Vertice banheiro = grafo.addVertice(
            "Banheiro"
        );

        grafo.conectar(quintal, casinhacachorro);
        grafo.conectar(casinhacachorro, quintal);

        grafo.conectar(quintal, cozinha);
        grafo.conectar(cozinha, quintal);

        grafo.conectar(sala, cozinha);
        grafo.conectar(cozinha, sala);

        grafo.conectar(quintal, sala);
        grafo.conectar(sala, quintal);
        
        grafo.conectar(sala, banheiro);

        Scanner leitor = new Scanner(System.in);
        Vertice posAtual = casinhacachorro;
        while(true) {
            System.out.println(posAtual.nome);
            System.out.println("\nConexões:");
            for(Vertice conn : posAtual.conexoes) {
                System.out.print(conn.nome+", ");
            }
            System.out.print("\n\n>");
            String entrada = leitor.nextLine();

            for(Vertice conn : posAtual.conexoes) {
                if(entrada.equalsIgnoreCase(conn.nome)) {
                    posAtual = conn;
                    break;
                }
            }
        }
    }
}
