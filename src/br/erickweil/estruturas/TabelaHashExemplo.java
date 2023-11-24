package br.erickweil.estruturas;

/*
Online Java - IDE, Code Editor, Compiler

Online Java is a quick and easy tool that helps you to build, compile, test your programs online.
*/
import java.util.Scanner;
public class TabelaHashExemplo
{
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String chave = "OK";
        String valor = "";
        String[] chaves = new String[10];
        String[] valores = new String[10];
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
            
            // ENQUANTO NÃO ACHAR UM ESPAÇO VAZIO SOMA 1
            while(chaves[indice] != null 
            && !chaves[indice].equals(chave)
            ) {
                indice = (indice + 1) % 10;
            }
            
            chaves[indice] = chave;
            valores[indice] = valor;
            
            for(int i=0;i<chaves.length;i++) { 
                System.out.println(i+":"+chaves[i]+" --> "+valores[i]); 
            }
        }
    }
}

