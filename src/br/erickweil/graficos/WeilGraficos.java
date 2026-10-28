package br.erickweil.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A ideia é facilitar desenhar no java, parecido com o Processing:
 * crie uma classe que herda de WeilGraficos, implemente o onDraw e chame iniciarGraficos().
 *
 * O onDraw e o onClick são chamados sempre pela mesma thread de desenho,
 * então não há problema em compartilhar atributos entre eles.
 * Aperte ESC para fechar a janela.
 * @author erick
 * https://edirlei.com/aulas/tp2_2014_2/TP2_Aula_05_Java2D_2014.html
 */
public abstract class WeilGraficos {
	// No Linux o pipeline OpenGL permite o vsync: o show() espera o monitor terminar de mostrar
	// o quadro anterior, então nenhum quadro é repetido ou perdido. O padrão (XRender) não tem vsync.
	// Precisa ser definido antes de qualquer janela existir, e respeita quem já escolheu outro valor.
	static {
		if(System.getProperty("os.name").startsWith("Linux") && System.getProperty("sun.java2d.opengl") == null)
			System.setProperty("sun.java2d.opengl", "true");
	}

	// Verdadeiro para redesenhar continuamente, falso para redesenhar apenas quando clicar.
	// Enquanto estiver falso o tempo fica pausado. Pode ser alterado a qualquer momento.
	public volatile boolean keepRedrawing = true;
	// Quantos quadros desenhar por segundo, o ideal é ser igual à taxa de atualização do monitor.
	// Ignorado quando o vsync funciona, aí a taxa é sempre a do monitor. Pode ser alterado a qualquer momento.
	public volatile int quadrosPorSegundo = 60;
	// Abre ocupando o monitor inteiro, sem bordas. Só tem efeito se alterado antes de chamar iniciarGraficos().
	public boolean telaCheia = false;

	// Limite do deltaTime. Se o programa parar por um tempo (ex: num breakpoint do depurador)
	// a animação continua de onde estava em vez de dar um salto enorme.
	private static final double MAX_DELTA_TIME = 1.0 / 3;
	// Quanto antes do prazo de cada quadro a thread de desenho para de dormir e passa a esperar ativamente,
	// pois o sistema operacional pode acordar a thread atrasada
	private static final long ESPERA_ATIVA_NS = 1_000_000;

	private final String titulo;
	private final int largura;
	private final int altura;

	// Criados só no iniciarGraficos, pois as janelas devem ser criadas na thread do Swing
	private Tela tela;
	private BufferStrategy estrategia;
	// Escritos pela thread do Swing (eventos do mouse e da janela) e lidos pela thread de desenho
	private volatile int mouseX, mouseY;
	private volatile boolean redesenhar;
	private volatile Throwable erro;
	// Cliques ainda não entregues ao onClick: {x, y, botão}
	private final ConcurrentLinkedQueue<int[]> cliques = new ConcurrentLinkedQueue<>();
	private int frameCount;

	/**
	 * @param largura e altura da área de desenho, sem contar as bordas da janela
	 */
	protected WeilGraficos(String titulo, int largura, int altura) {
		this.titulo = titulo;
		this.largura = largura;
		this.altura = altura;
	}

	/**
	 * Avança as animações e desenha a tela, chamado uma vez por quadro.
	 * Com keepRedrawing falso é chamado só quando precisa redesenhar (ex: ao clicar ou ao
	 * redimensionar a janela), nesse caso deltaTime é 0 pois o tempo está pausado.
	 * @param deltaTime tempo em segundos desde o quadro anterior.
	 * Multiplique velocidades por ele para a animação não depender da taxa de quadros.
	 */
	public abstract void onDraw(Graphics2D g, double deltaTime);

	/**
	 * Chamado ao clicar, logo antes do próximo onDraw.
	 * @param button 1 esquerdo, 2 meio e 3 direito
	 */
	public void onClick(int x, int y, int button) {}

	// Largura da área de desenho, acompanha o redimensionamento da janela
	public int getWidth() {
		return tela == null ? largura : tela.getWidth();
	}

	// Altura da área de desenho, acompanha o redimensionamento da janela
	public int getHeight() {
		return tela == null ? altura : tela.getHeight();
	}

	// Última posição do mouse sobre a tela
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	// Quantas vezes o onDraw foi chamado, contando o atual (1 no primeiro quadro)
	public int getFrameCount() {
		return frameCount;
	}

	/**
	 * Para tudo e mostra o erro na tela, em vez de repetir a mesma exceção no console a cada quadro.
	 */
	private void falhou(Throwable e) {
		erro = e;
		e.printStackTrace();
		tela.repaint();
	}

	/**
	 * Um Canvas em vez de um JPanel: com o BufferStrategy a thread de desenho desenha
	 * direto no buffer da janela e mostra com show(), sem passar pela thread do Swing.
	 */
	private class Tela extends Canvas {
		Tela(GraphicsConfiguration gc) {
			super(gc);
			setBackground(Color.BLACK);
			setPreferredSize(new Dimension(largura, altura));

			MouseAdapter mouse = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
					// Entregue pela thread de desenho, para o onClick não rodar ao mesmo tempo que o onDraw
					cliques.add(new int[] {e.getX(), e.getY(), e.getButton()});
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
			};
			addMouseListener(mouse);
			addMouseMotionListener(mouse);

			// Como no Processing, ESC fecha. Na tela cheia não há botão de fechar
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Tela.this);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
				}
			});
		}

		// O padrão é apagar a tela antes do paint, o que causaria flicker
		@Override
		public void update(Graphics g) {
			paint(g);
		}

		// Chamado pelo sistema quando a janela precisa ser redesenhada (ex: ao redimensionar ou ser descoberta)
		@Override
		public void paint(Graphics g) {
			if(erro == null) {
				redesenhar = true;
				return;
			}

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.RED);
			drawCenteredString(g, erro.toString(), new Rectangle(0, -15, getWidth(), getHeight()));
			drawCenteredString(g, "Veja o console para mais detalhes", new Rectangle(0, 15, getWidth(), getHeight()));
		}
	}

	/**
	 * Abre a janela e começa a desenhar usando o onDraw.
	 */
	public JFrame iniciarGraficos() {
		if(tela != null) throw new IllegalStateException("iniciarGraficos() só pode ser chamado uma vez");

		// O Swing exige que as janelas sejam criadas e alteradas na thread do Swing
		if(SwingUtilities.isEventDispatchThread()) {
			return criarJanela();
		}

		AtomicReference<JFrame> frame = new AtomicReference<>();
		try {
			SwingUtilities.invokeAndWait(() -> frame.set(criarJanela()));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Erro ao criar a janela", e.getCause());
		}
		return frame.get();
	}

	private JFrame criarJanela() {
		GraphicsConfiguration gc = configuracaoComPageFlipping();
		tela = new Tela(gc);

		JFrame frame = new JFrame(gc);
		frame.setTitle(titulo);
		frame.add(tela);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Ajusta a janela para a área de desenho ficar exatamente com largura x altura
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Dois buffers: desenha no escondido enquanto o outro está na tela
		tela.createBufferStrategy(2);
		estrategia = tela.getBufferStrategy();
		// Só depois de criar os buffers, se entrar em tela cheia antes o OpenGL recusa o page flipping
		if(telaCheia)
			gc.getDevice().setFullScreenWindow(frame);
		// Para receber o ESC
		tela.requestFocusInWindow();

		Thread desenho = new Thread(this::loopQuadros, "WeilGraficos-desenho");
		desenho.setDaemon(true);
		desenho.start();
		return frame;
	}

	/**
	 * O OpenGL só faz vsync numa configuração com page flipping (trocar o buffer mostrado
	 * em vez de copiar), e a configuração padrão dele não tem. As outras pipelines ficam com a padrão.
	 */
	private static GraphicsConfiguration configuracaoComPageFlipping() {
		GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsConfiguration padrao = monitor.getDefaultConfiguration();
		if(padrao.getBufferCapabilities().isPageFlipping()) return padrao;
		for(GraphicsConfiguration gc : monitor.getConfigurations())
			if(gc.getBufferCapabilities().isPageFlipping()) return gc;
		return padrao;
	}

	/**
	 * Mostra alguns quadros pretos o mais rápido possível: com vsync o show() espera o monitor
	 * e cada quadro leva um período inteiro do monitor, sem vsync leva bem menos de 1ms.
	 */
	private boolean detectarVsync() {
		int taxaMonitor = tela.getGraphicsConfiguration().getDevice().getDisplayMode().getRefreshRate();
		if(taxaMonitor <= 0) taxaMonitor = 60; // Desconhecida

		long[] duracoes = new long[15];
		for(int i = 0; i < duracoes.length; i++) {
			long inicio = System.nanoTime();
			Graphics g = estrategia.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
			g.dispose();
			estrategia.show();
			Toolkit.getDefaultToolkit().sync();
			duracoes[i] = System.nanoTime() - inicio;
		}
		// A mediana ignora os primeiros quadros, que demoram mais enquanto tudo é inicializado
		Arrays.sort(duracoes);
		return duracoes[duracoes.length / 2] > 1_000_000_000L / taxaMonitor / 2;
	}

	/**
	 * Com vsync o próprio show() marca o ritmo dos quadros. Sem vsync marca o ritmo com prazos em
	 * nanossegundos, pois o Timer do Swing só aceita milissegundos inteiros e 16ms dá mais quadros
	 * que o monitor de 60Hz consegue mostrar, então a animação engasga.
	 * Fica sempre rodando para que keepRedrawing possa ser ligado e desligado depois.
	 */
	private void loopQuadros() {
		boolean vsync = detectarVsync();
		// Instante do quadro anterior
		long anterior = System.nanoTime();
		while(erro == null) {
			long periodo = 1_000_000_000L / quadrosPorSegundo;
			long agora;
			if(vsync && keepRedrawing) {
				// O show() do quadro anterior já esperou o monitor
				agora = System.nanoTime();
			} else {
				// O deltaTime vem dos prazos e não da hora em que o quadro realmente começou,
				// assim os pequenos atrasos da thread não viram tremidas na animação
				agora = anterior + periodo;

				// Dorme até perto do prazo e termina esperando ativamente, para não acordar atrasado
				long falta;
				while((falta = agora - System.nanoTime()) > ESPERA_ATIVA_NS)
					LockSupport.parkNanos(falta - ESPERA_ATIVA_NS);
				while(System.nanoTime() < agora)
					Thread.onSpinWait();

				// Se atrasou mais de um quadro inteiro (ex: onDraw demorado) recomeça o ritmo a partir de agora,
				// em vez de desenhar uma rajada de quadros para tentar compensar
				if(System.nanoTime() - agora > periodo)
					agora = System.nanoTime();
			}
			double deltaTime = Math.min((agora - anterior) / 1e9, MAX_DELTA_TIME);
			anterior = agora;

			try {
				int[] clique;
				while((clique = cliques.poll()) != null) {
					onClick(clique[0], clique[1], clique[2]);
					redesenhar = true;
				}

				if(keepRedrawing) {
					desenharQuadro(deltaTime);
				} else if(redesenhar) {
					desenharQuadro(0);
				}
			} catch (Throwable e) {
				falhou(e);
			}
		}
	}

	private void desenharQuadro(double deltaTime) {
		redesenhar = false;
		Graphics2D g = (Graphics2D) estrategia.getDrawGraphics();
		try {
			// Depois do show() o conteúdo do buffer escondido é indefinido, então apaga antes de desenhar
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
			// Suaviza as bordas das formas e dos textos
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			frameCount++;
			onDraw(g, deltaTime);
		} finally {
			g.dispose();
		}

		estrategia.show();
		// No Linux sem isso o quadro pode ficar esperando num buffer até algo enviar ao servidor gráfico
		// (como o movimento do mouse) e a animação engasga
		Toolkit.getDefaultToolkit().sync();

		// O sistema pode descartar o buffer (ex: ao minimizar), aí desenha de novo no próximo quadro
		if(estrategia.contentsLost())
			redesenhar = true;
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
}
