
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

/**
 * Really simple turtle-graphics.
 *
 * Example of use:
 * <pre>
 * Turtle t = new Turtle();
 * 
 * // Draw a square
 * t.draw(1);
 * t.turn(-90);
 * t.draw(1);
 * t.turn(-90);
 * t.draw(1);
 * t.turn(-90);
 * t.draw(1);
 *
 */
public class Turtle {
	
	// ----------------------------------
	// UI
	
	JFrame window;
	
	Image baseImage;
	Image activeImage;
	
	Graphics2D baseGraphics;
	Graphics2D activeGraphics;
	
	JLabel imageLabel;
	
	// ----------------------------------
	// Parameters
	
	int width;
	int height;
	
	Color backgroundColor = Color.white;
	
	Stroke turtleStroke = new BasicStroke(2);
	
	// ----------------------------------
	// State
	
	double x;
	double y;
	double theta;
	
	Color color;
	Stroke lineStroke;
	
	double scale;
	
	double speed;
	
	double endX;
	double endY;
	
	// -------------------------------------------------------------
	
	/**
	 * Constructs a canvas that is 800x600 and displays the window.
	 * 
	 * When the window is closed the program will exit.
	 */
	public Turtle() {
		this(800, 600);
	}
	
	/**
	 * Constructs a canvas that is _width x _height and displays the window.
	 *
	 * When the window is closed the program will exit.
	 *
	 * @param _width  The width of the drawing canvas.
	 * @param _height The height of the drawing canvas.
	 */
	public Turtle(int _width, int _height) {
		this.width  = _width;
		this.height = _height;
		
		window = new JFrame("Turtle");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = window.getContentPane();
		
		imageLabel = new JLabel(new DummyIcon(width, height));
		
		cp.add(imageLabel);
		
		window.pack();
		
		window.setVisible(true);
		makeImage();
		
		clear();
	}
	
	// -------------------------------------------------------------
	
	/**
	 * Sets the speed of the turtle in pixels/second.
	 *
	 * Speed is used for draw() and move() but not for turn().
	 *
	 * @param _speed The speed in pixels/second.
	 */
	public void setSpeed(double _speed) {
		synchronized (this) {
			this.speed = _speed;
		}
	}
	
	/**
	 * Sets the line color.
	 *
	 * @param _color The line color.
	 */
	public void setColor(Color _color) {
		synchronized (this) {
			this.color = _color;
		}
	}
	
	/**
	 * Sets the line thickness.
	 *
	 * @param thick The line thickness.
	 */
	public void setThickness(int thick) {
		synchronized (this) {
			this.lineStroke = new BasicStroke(thick);
		}
	}
	
	/**
	 * Sets the pixels/inch.
	 *
	 * Defaults to 50 pixels/inch.
	 *
	 * @param _scale Pixels/inch.
	 */
	public void setScale(double _scale) {
		synchronized (this) {
			this.scale = _scale;
		}
	}
	
	// -------------------------------------------------------------
	
	/**
	 * Clears the canvas, and resets params to their default.
	 *
	 * Resets speed, scale, color, thickness. Centers the turtle and
	 * points it to the right.
	 */
	public void clear() {
		synchronized (this) {
			x = width/2;
			y = width/2;
			
			endX = x;
			endY = y;
			
			theta = 0.0;
			
			scale = 50.0;
			
			speed = 100.0;
			
			color = Color.black;
			lineStroke = new BasicStroke(1);
		}
		
		clearBase();
		
		updateScreenWrapper();
	}
	
	/**
	 * Draw a line a given distance.
	 *
	 * If the speed is infinite, the line will instantly appear and the turtle
	 * will end up in the new location.
	 *
	 * If the speed is finite, the turtle will traverse the line at the given
	 * speed, ending up at the endpoint.
	 *
	 * The turtle always moves when drawing lines.
	 *
	 * @param distance The distance in inches.
	 * @see #setScale
	 * @see #setSpeed
	 * @see #setColor
	 * @see #setThickness
	 */
	public void draw(double distance) {
		
		double totalDelay;
		
		synchronized (this) {
			distance *= scale;
			totalDelay = distance / speed;
		}
		
		moveSlowly(totalDelay, new LineUpdater(distance));
		
		synchronized (this) {
			endX = x + distance * Math.cos(theta);
			endY = y + distance * Math.sin(theta);
			
			fixLine();
			
			x = endX;
			y = endY;
		}
		
		updateScreenWrapper();
	}
	
	/**
	 * Moves the turtle forward in the direction it is facing.
	 *
	 * This behaves the same way as draw() except that no line appears. If the
	 * speed is finite the turtle will move along the line in the same manner
	 * as draw.
	 *
	 * @param distance The distance in inches.
	 * @see #setScale
	 * @see #setSpeed
	 */
	public void move(double distance) {
		
		double totalDelay;
		double startX, startY;
		
		synchronized (this) {
			distance *= scale;
			totalDelay = distance / speed;
			startX = x;
			startY = y;
		}
		
		moveSlowly(totalDelay, new MoveUpdater(distance));
		
		synchronized (this) {
			x = startX + distance * Math.cos(theta);
			y = startY + distance * Math.sin(theta);
			
			endX = x;
			endY = y;
		}
		
		updateScreenWrapper();
	}
	
	/**
	 * Jumps to an absolute location in pixels.
	 *
	 * 0,0 is upper left. Instant.
	 * 
	 * @param _x X coord in pixels.
	 * @param _y Y coord in pixels.
	 * */
	public void jump(double _x, double _y) {
		synchronized (this) {
			endX = x = _x;
			endY = y = _y;
		}
		
		updateScreenWrapper();
	}
	
	/**
	 * Turns the turtle dtheta degrees to the left.
	 *
	 * Turning is instant.
	 *
	 * @param dtheta Degrees left to turn.
	 * @see #turnRad
	 */
	public void turn(double dtheta) {
		synchronized (this) {
			theta += dtheta / 180.0 * Math.PI;
		}
		
		updateScreenWrapper();
	}
	
	/**
	 * Turns the turtle dtheta radians to the left.
	 *
	 * Turning is instant.
	 *
	 * @param dtheta Radians left to turn.
	 * @see #turn
	 */
	public void turnRad(double dtheta) {
		turn(dtheta * 180.0 / Math.PI);
	}
	
	/**
	 * Pause for `millis` milliseconds.
	 *
	 * @param millis Milliseconds to pause.
	 */
	public void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			return;
		}
	}
	
	// -------------------------------------------------------------
	
	private void fixLine()
	{
		draw(new LinePicture());
	}
	
	private void clearBase() {
		draw(new ClearPicture());
	}
	
	private void draw(final Picture pic) {
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			pic.draw(baseGraphics);
		}});
	}
	
	private void moveSlowly(double time, Updater updater) {
		double startTime = System.currentTimeMillis() / 1000.0;
		
		for (;;) {
			double nowTime = System.currentTimeMillis() / 1000.0;
			double partTime = nowTime - startTime;
			
			if (partTime >= time) {
				break;
			}
			
			double fraction = partTime / time;
			
			updater.update(fraction);
			
			try {
				Thread.sleep(20);
			}
			catch (InterruptedException ie) {
				return;
			}
		}
	}
	
	// -------------------------------------------------------------
	
	private void drawActiveLine() {
		synchronized (this) {
			new LinePicture().draw(activeGraphics);
		}
	}
	
	private void drawTurtle() {
		synchronized (this) {
			Graphics2D g = activeGraphics;
			
			g.setColor(color);
			g.setStroke(turtleStroke);
			
			AffineTransform saveT = g.getTransform();
			
			g.translate(endX, endY);
			g.rotate(theta);
			
			g.drawLine( -5,  0, 5, 0 );
			g.drawLine(  0,  5, 5, 0 );
			g.drawLine(  0, -5, 5, 0 );
			
			g.setTransform(saveT);
		}
	}
	
	private void updateScreenWrapper() {
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			updateScreen();
		}});
	}
	
	private void updateScreen() {
		makeImage();
		if (activeGraphics == null || baseGraphics == null) {
			return;
		}
		
		synchronized (this) {
			activeGraphics.drawImage(baseImage, 0, 0, null);
			
			drawActiveLine();
			drawTurtle();
		}
		
		imageLabel.repaint();
	}
	
	private void makeImage() {
		if (activeGraphics != null && baseGraphics != null) {
			return;
		}
		
		activeImage = window.createImage(width, height);
		baseImage   = window.createImage(width, height);
		
		if (activeImage != null)
			activeGraphics = (Graphics2D) activeImage.getGraphics();
		
		if (baseImage != null)
			baseGraphics = (Graphics2D) baseImage.getGraphics();
		
		if (activeImage != null) {
			imageLabel.setIcon(new ImageIcon(activeImage));
		}
	}
	
	// -----------------------------------------------------------------
	
	interface Picture {
		public void draw(Graphics2D g);
	}
	
	class LinePicture implements Picture {
		
		int x1;
		int y1;
		int x2;
		int y2;
		
		Color color;
		Stroke stroke;
		
		LinePicture() {
			x1 = (int) Math.round(x);
			y1 = (int) Math.round(y);
			x2 = (int) Math.round(endX);
			y2 = (int) Math.round(endY);
			
			color  = Turtle.this.color;
			stroke = Turtle.this.lineStroke;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(color);
			g.setStroke(stroke);
			
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	class ClearPicture implements Picture {
		
		Color color;
		
		ClearPicture() {
			color = Turtle.this.backgroundColor;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(color);
			g.fillRect(0, 0, width, height);
		}
	}
	
	interface Updater {
		public void update(double progress);
	}
	
	class LineUpdater implements Updater {
		
		double distance;
		
		LineUpdater(double _distance) {
			this.distance = _distance;
		}
		
		public void update(double progress) {
			double partDistance = progress * distance;
			
			synchronized (Turtle.this) {
				endX = x + partDistance * Math.cos(theta);
				endY = y + partDistance * Math.sin(theta);
			}
			
			updateScreenWrapper();
		}
	}
	
	class MoveUpdater implements Updater {
		
		double distance;
		double startX;
		double startY;
		
		MoveUpdater(double _distance) {
			this.distance = _distance;
			
			synchronized (Turtle.this) {
				startX = x;
				startY = y;
			}
		}
		
		public void update(double progress) {
			double partDistance = progress * distance;
			
			synchronized (Turtle.this) {
				endX = startX + partDistance * Math.cos(theta);
				endY = startY + partDistance * Math.sin(theta);
				
				x = endX;
				y = endY;
			}
			
			updateScreenWrapper();
		}
	}
	
	static class DummyIcon implements Icon {
		
		int width;
		int height;
		
		public DummyIcon(int _width, int _height) {
			this.width  = _width;
			this.height = _height;
		}
		
		public void paintIcon(Component comp, Graphics g, int w, int h) {
			// Do nothing
		}
		
	    public int getIconWidth() {
			return width;
		}
		
	    public int getIconHeight() {
			return height;
		}
		
	}
}
