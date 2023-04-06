package br.erickweil.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.erickweil.arvores.Arvore;
import br.erickweil.estruturas.Pilha;
import br.erickweil.estruturas.Referencias;
import br.erickweil.estruturas.Referencias.Corrente;
import br.erickweil.estruturas.Referencias.Elo;
import br.erickweil.utilidades.WeilArrays;

/**
 * A ideia é desenhar as referências que um objeto faz a outros, atravessando os atributos dele com Reflexão
 * @author erick
 * https://cs111.wellesley.edu/~cs111/archive/cs111_fall97/public_html/lectures/ObjectDiagrams/object-diagrams.html
 * 
 * https://www.ocf.berkeley.edu/~eek/index.html/tiny_examples/thinktank/src/gv1.7c/doc/dotguide.pdf
 */
public class DesenharReferenciasObjeto {

	public static class Teste {
		public static int count;
		int valor;
		int[] valores;
		String nome;
		String[] nomes;
		Teste ref;
		
		
		public Teste(Random rdn) {
			count++;
			
			valor = rdn.nextInt(100);
			valores = rdn.nextBoolean() ? null : new int[rdn.nextInt(10)+1];
			if(valores != null)
			WeilArrays.iniciarAleatorio(valores, 100);
			nome = rdn.nextLong()+"";
			ref = rdn.nextBoolean() || count > 50 ? null : new Teste(rdn);
			nomes = rdn.nextBoolean() || count > 50 ? null : new String[rdn.nextInt(10)+1];
			
			if(nomes != null) for(int i=0;i<nomes.length;i++) {
				if(rdn.nextBoolean()) {
					nomes[i] =  rdn.nextLong()+"";
				}
			}
		}
	}
	
	public static void main(String[] args) {
		/*Corrente corrente = new Corrente();
		
		Elo a = new Elo("A");
		corrente.adicionarElo(a);
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		corrente.adicionarElo(new Elo("H"));
		
		a.proximo = corrente.primeiro;*/
		
		/*Pilha pilha = new Pilha(10);
		pilha.push(10);
		pilha.push(5);
		pilha.push(2);
		pilha.push(1);
		pilha.push(77);*/
		
		Arvore arvore = DesenharArvore.gerarNovaArvore();
		
		// new Teste(new Random())
		DesenharReferenciasObjeto des = new DesenharReferenciasObjeto(arvore);
		
		String dot = des.gerarDOT();
		
		System.out.println(dot);
	}
	
	Object objeto;
	DesenharObjeto primeiro;
	public static class DesenharAtributo {
		String nome;
		String valor;
		DesenharObjeto ref;
		
		public DesenharAtributo(String nome, String valor) {
			this.nome = nome;
			this.valor = valor;
		}
		
		public String getLabel() {
			String n = nome.isBlank() ? "" : "<"+nome+">"+nome;
			
			if(ref != null) {
				return "<"+nome+">"+nome;
			} else {
				if(nome.isBlank())
					return valor;
				else 
					return nome+"="+valor;
			}
		}
	}
	
	public static class DesenharObjeto {
		String classe;
		int count; // #n da mesma classe
		DesenharAtributo[] atributos;
		boolean atravessou;
		
		public String DOTRef() {
			return DOTNome()+"_"+atributos[0].nome;
		}
		
		public String DOTNome() { return classe+"_"+count; }
		
		public void gerarDOT(StringBuilder sb,int nivel) {
			if(atravessou) return;
			atravessou = true;
			
			String attrLabel = "{"+classe+"}|{";
			// declarar todas as referências primeiro
			for(int i=0;i<atributos.length;i++) {
				if(atributos[i] == null) continue;
				
				if(i > 0) attrLabel += "|";
				attrLabel += atributos[i].getLabel();
				
				if(atributos[i].ref == null) continue;
				
				atributos[i].ref.gerarDOT(sb,nivel+1);
			}
			attrLabel += "}";
			
			// a [label="{foo|bar}"];
			sb.append(DOTNome()+" [label=\""+attrLabel+"\"]; \n");
			
			for(int i=0;i<atributos.length;i++) {
				if(atributos[i] == null) continue;				
				if(atributos[i].ref == null) continue;
				DesenharAtributo attr = atributos[i];
				
				
				sb.append(DOTNome()+":"+attr.nome+" -> "+attr.ref.DOTNome()+";\n");
			}
			
		}
		
		public void desatravessar() {
			if(atravessou == false) return;
			atravessou = false;
			
			for(int i=0;i<atributos.length;i++) {
				if(atributos[i] != null && atributos[i].ref != null) {
					atributos[i].ref.desatravessar();
				}
			}
		}
	}
	
	public DesenharReferenciasObjeto(Object objeto) {
		this.objeto = objeto;
		
		this.primeiro = analisarObjeto(objeto, new HashMap<>(), new HashMap<>());
	}
	
	public String gerarDOT() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("digraph referencias {\n");
		sb.append("compound = true;\n");
		sb.append("node [ shape = \"Mrecord\" ];\n");
		this.primeiro.desatravessar();
		this.primeiro.gerarDOT(sb,0);
		
		sb.append("}\n");
		
		return sb.toString();
	}
	
	public DesenharObjeto analisarObjeto(Object obj,Map<String,Integer> classesCount,Map<Object, DesenharObjeto> visitados) {
		if(obj == null) return null;
		if(visitados.containsKey(obj)) return visitados.get(obj);
				
		DesenharObjeto desObj = new DesenharObjeto();
		
		visitados.put(obj, desObj);
		
		Class<?> classe = obj.getClass();
		desObj.classe = classe.getSimpleName();
		if(desObj.classe.contains("[")) {
			desObj.classe = desObj.classe.replace("[]", "")+"_Array";
		}
		
		classesCount.put(desObj.classe , classesCount.getOrDefault(desObj.classe , 0) + 1);
		desObj.count = classesCount.get(desObj.classe);
		
		if(classe == String.class) {
			String v = (String) obj;
			desObj.atributos = new DesenharAtributo[] {new DesenharAtributo("", v)};
			
		} else if(classe.isArray()) {
			int size = Array.getLength(obj);
			Class<?> componentType = classe.componentType();
			desObj.atributos = new DesenharAtributo[size];
			for(int i=0;i<size;i++) {
				Object arrayValue = Array.get(obj, i);
				desObj.atributos[i] = new DesenharAtributo(""+i,""+arrayValue);
				
				if(!componentType.isPrimitive())
				{
					desObj.atributos[i].ref = analisarObjeto(arrayValue,classesCount,visitados);
				}
			}
		} else {
			List<Field> fields = new ArrayList<>();
			getAllFields(fields,classe);
			desObj.atributos = new DesenharAtributo[fields.size()];
	        for(int i = 0; i < fields.size(); i++) {
	        	Field f = fields.get(i);
	
	        	Object f_value = null;
	        	try {
	        	f_value = f.get(obj);
	        	} catch (Exception e) {}
	        	
	        	desObj.atributos[i] = new DesenharAtributo(f.getName(),""+f_value);
	        	if(!f.getType().isPrimitive() /*&& f.getType() != String.class*/) {
	        		desObj.atributos[i].ref = analisarObjeto(f_value,classesCount,visitados);
	        	}
	        }
		}
        
        //if(desObj.atributos.length == 0) {
        //	desObj.atributos = new DesenharAtributo[] {new DesenharAtributo("ref", null)};
        //}
        
        return desObj;
	}
	
	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}
