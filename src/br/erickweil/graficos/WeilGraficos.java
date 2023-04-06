package br.erickweil.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A ideia é facilitar desenhar no java
 * @author erick
 * https://edirlei.com/aulas/tp2_2014_2/TP2_Aula_05_Java2D_2014.html
 */
public class WeilGraficos extends JPanel implements Runnable, MouseListener {
	
	public static interface Callback {
		public void onDraw(Graphics2D g, int w, int h);
		public void onClick(int x, int y, int button);
	}

	private Callback callback;
	
	private WeilGraficos(Callback callback) {
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.BLACK);
		this.callback = callback;
		//new Thread(this).start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();
		
		Graphics2D g2d = (Graphics2D) g;
		if(callback != null) {
			callback.onDraw(g2d,w,h);
		} else {
			g2d.setColor(Color.WHITE);
			g2d.drawString("NENHUM CALLBACK", w/2, h/2);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static JFrame iniciarGraficos(Callback callback) {
		
		WeilGraficos wg = new WeilGraficos(callback);
		JFrame frame = new JFrame();
		frame.setTitle("Java 2D");
		frame.add(wg);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(wg);
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
	    Font font = g.getFont();
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public static void main(String[] args) {
		iniciarGraficos(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		 Point p = e.getPoint();
		if(callback != null) callback.onClick(p.x, p.y, e.getButton());
		
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
