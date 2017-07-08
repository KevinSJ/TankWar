import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

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
	private static final Random r = new Random();
	private boolean good;
	private boolean live = true;
	TankClient tc;

	private boolean bL = false, bU = false, bR = false, bD = false;
	private int step = r.nextInt(12) + 3;
	private Direction dir = Direction.STEADY;
	private Direction barrelDir = Direction.D;

	/**
	 * @param x
	 * @param y
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
		this.dir = dir;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good)
				tc.ets.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(good ? Color.RED : Color.BLUE);
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

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.WIDTH)
			x = TankClient.WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.HEIGHT)
			y = TankClient.HEIGHT - Tank.HEIGHT;
		if (!good) {
			if (step == 0) {
				step = r.nextInt(12) + 3;
				this.dir = Direction.values()[r.nextInt(Direction.values().length)];
			}
			step--;
			if (r.nextInt(30) > 28)
				this.fire();
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

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
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
		if (live)
			tc.ms.add(new Missile(this.x + WIDTH / 2 - Missile.getWidth() / 2,
					this.y + HEIGHT / 2 - Missile.getHeight() / 2, good, barrelDir, this.tc));
	}

	/**
	 * @return the live
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * @param live
	 *            the live to set
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * @return the good
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * @param good
	 *            the good to set
	 */
	public void setGood(boolean good) {
		this.good = good;
	}
}
