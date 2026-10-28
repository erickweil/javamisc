package br.erickweil.graficos;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * A ideia é facilitar desenhar no java
 * @author erick
 * https://edirlei.com/aulas/tp2_2014_2/TP2_Aula_05_Java2D_2014.html
 */
public class WeilGraficos extends JPanel {
	/**
	 * Todos os métodos são chamados pela thread do Swing (Event Dispatch Thread),
	 * então não há problema em compartilhar atributos entre eles.
	 */
	public static interface Callback {
		/**
		 * Desenha a tela. Pode ser chamado mais vezes que o esperado
		 * (ex: ao redimensionar a janela), então evite alterar o estado da simulação aqui.
		 */
		public void onDraw(Graphics2D g, int w, int h);
		public void onClick(int x, int y, int button);

		/**
		 * Chamado uma vez por quadro, antes de desenhar, quando keepRedrawing é verdadeiro.
		 * É o lugar para avançar animações e simulações.
		 */
		public default void onUpdate() {}
	}

	// Intervalo entre os quadros em milissegundos (16ms ~ 60 quadros por segundo)
	public int intervaloQuadro = 16;
	private transient Callback callback;

	private WeilGraficos(Callback callback,boolean keepRedrawing) {
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.BLACK);
		this.callback = callback;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(WeilGraficos.this.callback != null) 
					WeilGraficos.this.callback.onClick(e.getX(), e.getY(), e.getButton());
				repaint();
			}
		});

		// O Timer do Swing executa na thread do Swing, ao contrário de uma Thread própria
		if(keepRedrawing) {
			new Timer(intervaloQuadro, e -> {
				if(this.callback != null) 
					this.callback.onUpdate();
				repaint();
			}).start();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();

		Graphics2D g2d = (Graphics2D) g;
		// Suaviza as bordas das formas e dos textos
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if(callback != null) {
			callback.onDraw(g2d,w,h);
		} else {
			g2d.setColor(Color.WHITE);
			drawCenteredString(g2d, "NENHUM CALLBACK", new Rectangle(0, 0, w, h));
		}
	}

	/**
	 * Abre uma janela que desenha usando o callback.
	 * @param keepRedrawing verdadeiro para redesenhar continuamente (~60 vezes por segundo),
	 * falso para redesenhar apenas quando clicar.
	 */
	public static JFrame iniciarGraficos(Callback callback,boolean keepRedrawing) {
		// O Swing exige que as janelas sejam criadas e alteradas na thread do Swing
		if(SwingUtilities.isEventDispatchThread()) {
			return criarJanela(callback, keepRedrawing);
		}

		AtomicReference<JFrame> frame = new AtomicReference<>();
		try {
			SwingUtilities.invokeAndWait(() -> frame.set(criarJanela(callback, keepRedrawing)));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Erro ao criar a janela", e.getCause());
		}
		return frame.get();
	}

	private static JFrame criarJanela(Callback callback,boolean keepRedrawing) {
		JFrame frame = new JFrame();
		frame.setTitle("Java 2D");
		frame.add(new WeilGraficos(callback,keepRedrawing));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}

	/** https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	public static void drawCenteredString(Graphics g, String text, Rectangle rect) {
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics();
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(text, x, y);
	}

	public static void main(String[] args) {
		iniciarGraficos(null,false);
	}
}
