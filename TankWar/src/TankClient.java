import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6735016569246334982L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	int x = 50, y = 50;

	Image offScreenImg = null;

	public void launchFrame() {
		this.setLocation(400, 300);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Tank War");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		new Thread(new PaintThread()).start();
		// Java 8 Lambda Version of Thread.
		// new Thread(() -> {
		// while (true) {
		// repaint();
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		super.update(g);
		if (offScreenImg == null)
			offScreenImg = this.createImage(WIDTH, HEIGHT);
		Graphics graphics = offScreenImg.getGraphics();
		Color c = graphics.getColor();
		graphics.setColor(Color.GREEN);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		paint(graphics);
		graphics.setColor(c);
		g.drawImage(offScreenImg, 0, 0, null);
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class KeyMonitor extends KeyAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			System.out.println("Ok" + e.getKeyChar());
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				x -= 5;
				break;
			case KeyEvent.VK_UP:
				y -= 5;
				break;
			case KeyEvent.VK_RIGHT:
				x += 5;
				break;
			case KeyEvent.VK_DOWN:
				y += 5;
				break;
			}
		}

	}
}
