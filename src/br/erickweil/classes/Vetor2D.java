package br.erickweil.classes;

public class Vetor2D {
	double x;
	double y;
	
	public Vetor2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vetor2D somar(Vetor2D outro) {
		return new Vetor2D(
			this.x + outro.x,
			this.y + outro.y
		);
	}
	
	public Vetor2D subtrair(Vetor2D outro) {
		return new Vetor2D(
			this.x - outro.x,
			this.y - outro.y
		);
	}
	
	public double distanciaAte(Vetor2D outro) {
		Vetor2D d = this.subtrair(outro);
		return Math.sqrt(d.x * d.x + d.y * d.y);
	}
	
	public String toString() {
		return "[X:"+this.x+" Y:"+this.y+"]";
	}
}
