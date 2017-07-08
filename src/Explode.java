import java.awt.Color;
import java.awt.Graphics;


/**
 * 
 */

/**
 * @author kevin
 *
 */
public class Explode {
	
	private TankClient tc;
	
	int x,y;
	private boolean live = true;
	int[] diameter =  {4,12,18,26,32,49,30,14,6};
	int step = 0;
	
	
	
	/**
	 * @param tc
	 * @param x
	 * @param y
	 */
	public Explode(TankClient tc, int x, int y) {
		this.setTc(tc);
		this.x = x;
		this.y = y;
	}



	public void draw(Graphics g){
		if(!live){
			tc.es.remove(this);
			return;
		}
		
		if(step == diameter.length){
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}



	public TankClient getTc() {
		return tc;
	}



	public void setTc(TankClient tc) {
		this.tc = tc;
	}
	
	
}
