import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 
 */

/**
 * @author kevin
 *
 */
public class Tank {
	private int x, y;
	private static final int X_SPEED = 5;
	private static final int Y_SPEED = 5;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;

	TankClient tc;

	private boolean bL = false, bU = false, bR = false, bD = false;

	private Direction dir = Direction.STEADY;
	private Direction barrelDir = Direction.D;

	/**
	 * @param x
	 * @param y
	 */
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tank(int x, int y, TankClient tc) {
		this(x, y);
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		switch (barrelDir) {
		case L:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + HEIGHT / 2, y);
			break;
		case RU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y);
			break;
		case R:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT);
			break;
		case D:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y + HEIGHT);
			break;
		case LD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT);
			break;
		case STEADY:
			break;
		}
		g.setColor(c);
		move();
	}

	public void move() {
		switch (dir) {
		case L:
			x -= X_SPEED;
			break;
		case LU:
			x -= X_SPEED;
			y -= Y_SPEED;
			break;
		case U:
			y -= Y_SPEED;
			break;
		case RU:
			x += X_SPEED;
			y -= Y_SPEED;
			break;
		case R:
			x += X_SPEED;
			break;
		case RD:
			x += X_SPEED;
			y += Y_SPEED;
			break;
		case D:
			y += Y_SPEED;
			break;
		case LD:
			x -= X_SPEED;
			y += Y_SPEED;
			break;
		case STEADY:
			break;
		}
		if (this.dir != Direction.STEADY) {
			this.barrelDir = this.dir;
		}
		if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HEIGHT) {
			
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDir();
	}

	public void keyRelease(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDir();

	}

	public void locateDir() {
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STEADY;
	}

	public void fire() {
		tc.ms.add(new Missile(this.x + WIDTH / 2 - Missile.getWidth() / 2,
				this.y + HEIGHT / 2 - Missile.getHeight() / 2, barrelDir, this.tc));
	}
}
