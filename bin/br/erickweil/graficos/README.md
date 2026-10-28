# Desenhando com o WeilGraficos

O `WeilGraficos` abre uma janela e chama o seu código **60 vezes por segundo** para desenhar nela. Cada desenho é chamado de **quadro** (*frame*), igual a um desenho animado: vários desenhos parados mostrados rápido parecem movimento.

## 1. Seu primeiro desenho

Crie uma classe que **herda** (`extends`) de `WeilGraficos`:

```java
import java.awt.Color;
import java.awt.Graphics2D;

import br.erickweil.graficos.WeilGraficos;

public class PrimeiroDesenho extends WeilGraficos {

	public PrimeiroDesenho() {
		super("Meu primeiro desenho", 800, 600);
	}

	@Override
	public void onDraw(Graphics2D g, double deltaTime) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight()); // pinta o fundo de branco

		g.setColor(Color.BLUE);
		g.fillRect(100, 100, 200, 150); // retângulo azul

		g.setColor(Color.RED);
		g.fillOval(400, 200, 100, 100); // círculo vermelho

		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 800, 600); // linha preta
		g.drawString("Olá!", 50, 50); // texto
	}

	public static void main(String[] args) {
		new PrimeiroDesenho().iniciarGraficos();
	}
}
```

São três partes:

- **O construtor** chama `super(titulo, largura, altura)` para escolher o título e o tamanho da janela.
- **O `onDraw`** é onde você desenha. Ele é chamado a cada quadro.
- **O `main`** cria o objeto e chama `iniciarGraficos()` para abrir a janela.

Para fechar, aperte **ESC** ou clique no X da janela.

## 2. As coordenadas da tela

A posição `(0, 0)` é o **canto superior esquerdo**. O `x` cresce para a direita e o `y` cresce **para baixo**, ao contrário da matemática da escola:

```
(0,0) ──────► x
  │
  │     (100, 50) fica 100 para a direita e 50 para baixo
  ▼
  y
```

Use `getWidth()` e `getHeight()` para saber a largura e a altura da tela. Assim seu desenho continua certo mesmo se a janela for redimensionada.

## 3. Formas e cores

Primeiro escolha a cor com `g.setColor(...)`, depois desenhe. A cor vale para tudo que vier depois, até você trocar de novo.

| Comando | O que desenha |
|---|---|
| `g.fillRect(x, y, largura, altura)` | Retângulo preenchido |
| `g.drawRect(x, y, largura, altura)` | Só o contorno do retângulo |
| `g.fillOval(x, y, largura, altura)` | Círculo ou elipse preenchido |
| `g.drawOval(x, y, largura, altura)` | Só o contorno do círculo |
| `g.drawLine(x1, y1, x2, y2)` | Linha de um ponto a outro |
| `g.drawString("texto", x, y)` | Texto, com o `y` sendo a base das letras |

**Cuidado com o círculo:** o `x` e o `y` do `fillOval` são o canto do quadrado em volta do círculo, **não o centro**. Para desenhar um círculo de raio `r` com centro em `(cx, cy)`:

```java
g.fillOval(cx - r, cy - r, r * 2, r * 2);
```

Algumas cores prontas: `Color.RED`, `Color.GREEN`, `Color.BLUE`, `Color.YELLOW`, `Color.ORANGE`, `Color.BLACK`, `Color.WHITE` e `Color.GRAY`. Para qualquer outra cor, misture vermelho, verde e azul com valores de 0 a 255:

```java
g.setColor(new Color(255, 128, 0)); // laranja
```

O fundo começa **preto** a cada quadro. Se quiser outra cor de fundo, pinte a tela inteira no começo do `onDraw`, como no primeiro exemplo.

## 4. Animação

Para algo se mover, guarde a posição num **atributo** e mude um pouquinho a cada quadro:

```java
public class QuadradoAndando extends WeilGraficos {

	// Atributos guardam valores entre um quadro e outro
	double x = 0;
	double velocidade = 200; // pixels por segundo

	public QuadradoAndando() {
		super("Quadrado andando", 800, 600);
	}

	@Override
	public void onDraw(Graphics2D g, double deltaTime) {
		// Atualiza: anda um pouco para a direita
		x = x + velocidade * deltaTime;
		if(x > getWidth()) {
			x = -50; // saiu pela direita, volta pela esquerda
		}

		// Desenha
		g.setColor(Color.ORANGE);
		g.fillRect((int) x, 250, 50, 50);
	}

	public static void main(String[] args) {
		new QuadradoAndando().iniciarGraficos();
	}
}
```

Três detalhes importantes:

- **Use atributo, não variável local.** Uma variável criada dentro do `onDraw` começa do zero em todo quadro, então o quadrado nunca sairia do lugar.
- **O `deltaTime`** é o tempo em segundos desde o quadro anterior (cerca de 0,016 s). Multiplicar a velocidade por ele faz o quadrado andar **200 pixels por segundo**, não importa quantos quadros por segundo o computador consiga desenhar.
- **O `(int) x`** converte o `double` para `int`, porque os comandos de desenho só aceitam números inteiros.

## 5. Mouse

`getMouseX()` e `getMouseY()` dizem onde o mouse está. Para reagir a um clique, escreva o método `onClick`:

```java
public class SeguirMouse extends WeilGraficos {

	Color cor = Color.RED;

	public SeguirMouse() {
		super("Seguir o mouse", 800, 600);
	}

	@Override
	public void onDraw(Graphics2D g, double deltaTime) {
		g.setColor(cor);
		// Subtrai 25 para o centro do círculo ficar no mouse
		g.fillOval(getMouseX() - 25, getMouseY() - 25, 50, 50);
	}

	@Override
	public void onClick(int x, int y, int button) {
		if(button == 1) {
			cor = Color.BLUE;  // botão esquerdo
		} else {
			cor = Color.RED;   // outros botões
		}
	}

	public static void main(String[] args) {
		new SeguirMouse().iniciarGraficos();
	}
}
```

O `button` vale 1 para o botão esquerdo, 2 para o do meio e 3 para o direito.

## 6. Opções

Altere estas opções no construtor, logo depois do `super(...)`:

```java
public MeuDesenho() {
	super("Meu desenho", 800, 600);
	telaCheia = true;       // ocupa o monitor inteiro (ESC fecha)
	keepRedrawing = false;  // só redesenha quando clicar
}
```

- **`telaCheia`:** abre ocupando o monitor inteiro, sem bordas.
- **`keepRedrawing = false`:** serve para desenhos parados, que só mudam ao clicar, como em `DesenharArvore`.
- **`getFrameCount()`:** diz quantos quadros já foram desenhados.

## 7. Quando dá erro

Se o seu código lançar uma exceção, a animação para e a mensagem aparece **em vermelho na janela**. Os detalhes, incluindo a linha do erro, ficam no **console**.

Erros comuns:

- **Esquecer o `setColor`.** A forma sai com a cor do desenho anterior.
- **Usar `Thread.sleep` ou um laço `while` para animar dentro do `onDraw`.** Não faça isso: o `onDraw` já é chamado a cada quadro, então basta mudar um pouquinho a cada chamada.
- **Esquecer o `iniciarGraficos()` no `main`.** A janela nem abre.

## Próximos passos

Veja `BolaQuicando.java` nesta pasta: uma bola com gravidade que quica nas bordas da tela, usando a classe `Vector2` para a posição e a velocidade.
