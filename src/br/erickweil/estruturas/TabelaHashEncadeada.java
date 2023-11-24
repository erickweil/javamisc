package br.erickweil.estruturas;

/*
Online Java - IDE, Code Editor, Compiler

Online Java is a quick and easy tool that helps you to build, compile, test your programs online.
*/
import java.util.Scanner;
import java.util.LinkedList;
public class TabelaHashEncadeada
{
    public static class ParChaveValor {
        String chave;
        String valor;
    }
    
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String chave = "OK";
        String valor = "";
        LinkedList<ParChaveValor>[] lista = new LinkedList[10];
        while(chave.length() > 0) {
            System.out.println();
            System.out.println("Insira uma chave:");
            chave = leitor.nextLine();
            System.out.println("Insira um valor:");
            valor = leitor.nextLine();
            
            int hash = chave.hashCode();
            int indice = Math.abs(hash % 10);
            System.out.println("Hash de '"+chave+"' é:"+hash);
            System.out.println("O índice é:"+indice);
            
            ParChaveValor par = new ParChaveValor();
            par.chave = chave;
            par.valor = valor;
            
            if(lista[indice] == null) {
                lista[indice] = new LinkedList<>();
            }
            lista[indice].add(par);
            
            int i=0;
            for(LinkedList<ParChaveValor> sublista : lista) { 
                System.out.print(i+":"); 
                if(sublista != null){
                    for(ParChaveValor v : sublista) { 
                    System.out.print(" "+v.chave+","+v.valor); 
                    }  
                }
                System.out.println(); 
                i++;
            }
        }
    }
}
