import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * 
 */

/**
 * @author kevin
 *
 */
public class Missile {
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private static final int X_SPEED = 10;
	private static final int Y_SPEED = 10;
	private boolean live = true;
	private int x, y;
	private Direction dir;
	private TankClient tc;
	private boolean good;

	/**
	 * @param x
	 * @param y
	 * @param dir
	 * @param tc
	 */
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.setGood(good);
		this.tc = tc;
	}

	/**
	 * @param x
	 * @param y
	 * @param dir
	 */
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.ms.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
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

		if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HEIGHT) {
			live = false;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	/**
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t) {
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			t.setLive(false);
			this.live = false;
			tc.es.add(new Explode(tc, x, y));
			return true;
		}
		return false;
	}

	public boolean hitTanks(List<Tank> tanks) {
		//if any of the Tanks in the list is hit, return true.
		return tanks.stream().filter(t -> hitTank(t) == true).findFirst().isPresent();
	}

	/**
	 * @return the width
	 */
	public static int getWidth() {
		return WIDTH;
	}

	/**
	 * @return the height
	 */
	public static int getHeight() {
		return HEIGHT;
	}

	/**
	 * @return the live
	 */
	public boolean isLive() {
		return live;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

}
