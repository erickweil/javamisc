package br.erickweil.graficos;

import java.awt.Color;
import java.awt.Graphics2D;

import br.erickweil.classes.Vector2;

/**
 * Uma bola sob efeito da gravidade quicando nas bordas da janela.
 * Clique para reposicionar a bola.
 */
public class BolaQuicando extends WeilGraficos {

	public static void main(String[] args) {
		new BolaQuicando().iniciarGraficos();
	}

	public BolaQuicando() {
		super("Bola Quicando", 800, 600);
	}

	Vector2 posicao = new Vector2(100, 100);
	Vector2 velocidade = new Vector2(200, 0); // pixels por segundo
	Vector2 aceleracao = new Vector2(0, 980);  // gravidade, positivo porque o y cresce para baixo
	double raio = 20;

	@Override
	public void onDraw(Graphics2D g, double deltaTime) {
		// Vector2 é imutável, então cada operação retorna um novo vetor que precisa ser atribuído
		velocidade = velocidade.add(aceleracao.multiply(deltaTime));
		posicao = posicao.add(velocidade.multiply(deltaTime));

		// Ao bater numa parede volta a bola para dentro e reflete a velocidade usando a normal da parede
		if(posicao.x - raio < 0) {
			posicao = new Vector2(raio, posicao.y);
			velocidade = velocidade.reflect(Vector2.RIGHT);
		}
		if(posicao.x + raio > getWidth()) {
			posicao = new Vector2(getWidth() - raio, posicao.y);
			velocidade = velocidade.reflect(Vector2.LEFT);
		}
		if(posicao.y - raio < 0) {
			posicao = new Vector2(posicao.x, raio);
			velocidade = velocidade.reflect(Vector2.DOWN);
		}
		if(posicao.y + raio > getHeight()) {
			posicao = new Vector2(posicao.x, getHeight() - raio);
			velocidade = velocidade.reflect(Vector2.UP);
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.RED);
		g.fillOval((int)(posicao.x - raio), (int)(posicao.y - raio), (int)(raio * 2), (int)(raio * 2));
	}

	@Override
	public void onClick(int x, int y, int button) {
		posicao = new Vector2(x, y);
		velocidade = new Vector2(200, 0);
	}
}
