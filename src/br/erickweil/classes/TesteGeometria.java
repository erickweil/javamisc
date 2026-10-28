package br.erickweil.classes;

public class TesteGeometria {

	public static void main(String[] args) {
		Vector2 pontoA = new Vector2(10, 15);
		Vector2 pontoB = new Vector2(0, 10);

		System.out.println(pontoA);
		System.out.println(pontoB);
		System.out.println("Distancia:"+pontoA.distanceTo(pontoB));
	}

}
