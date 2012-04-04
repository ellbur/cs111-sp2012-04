
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class Turtle {
	
	// ----------------------------------
	// UI
	
	static JFrame window;
	
	static int imageWidth  = 640;
	static int imageHeight = 480;
    static int minX = 0, minY = 0, maxX = imageWidth, maxY = imageHeight;
    static int shiftX, shiftY;
	
	static Image baseImage;
	static Image activeImage;
	
	static Graphics2D baseGraphics;
	static Graphics2D activeGraphics;
	
	static JLabel imageLabel;
	
	// ----------------------------------
	// Parameters
	
	static Color backgroundColor = Color.white;
	
	static Stroke turtleStroke = new BasicStroke(2);
	
	// ----------------------------------
	// State
	
	static double x;
	static double y;
	static double theta;
	
	static Color color;
	static Stroke lineStroke;
	
	static double speed;
	
	static double endX;
	static double endY;
    
    static Object lock = new Object();
    static boolean inited;
	
	// -------------------------------------------------------------
	
    private static void init() {
        if (inited) return;
        inited = true;
        
		window = new JFrame("Turtle");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = window.getContentPane();
		
		imageLabel = new JLabel(new DummyIcon(imageWidth, imageHeight));
		
		cp.add(imageLabel);
		
		window.pack();
		
		window.setVisible(true);
		makeImage();
		
		clear();
    }

    private static void ensureAround(int nx, int ny) {
        ensure(nx-10, ny-10);
        ensure(nx-10, ny+10);
        ensure(nx+10, ny-10);
        ensure(nx+10, ny+10);
    }
    
    private static void ensure(int nx, int ny) {
        if (nx < minX) reduceMinX(nx);
        if (nx > maxX) increaseMaxX(nx);
        if (ny < minY) reduceMinY(ny);
        if (ny > maxY) increaseMaxY(ny);
        
        if (nx < shiftX) shiftX = nx;
        if (nx > imageWidth+shiftX) shiftX = nx - imageWidth;
        if (ny < shiftY) shiftY = ny;
        if (ny > imageHeight+shiftY) shiftY = ny - imageHeight;
    }
    
    private static void reduceMinX(int nx) {
        Image newImage = window.createImage(maxX-nx, maxY-minY);
        Graphics2D newGraphics = (Graphics2D) newImage.getGraphics();
        
        newGraphics.setColor(Color.white);
        newGraphics.fillRect(0, 0, maxX-nx, maxY-minY);
        newGraphics.drawImage(baseImage, minX-nx, 0, null);
        minX = nx;
        
        baseImage = newImage;
        baseGraphics = newGraphics;
    }
    
    private static void reduceMinY(int ny) {
        Image newImage = window.createImage(maxX-minX, maxY-ny);
        Graphics2D newGraphics = (Graphics2D) newImage.getGraphics();
        
        newGraphics.setColor(Color.white);
        newGraphics.fillRect(0, 0, maxX-minX, maxY-ny);
        newGraphics.drawImage(baseImage, 0, minY-ny, null);
        minY = ny;
        
        baseImage = newImage;
        baseGraphics = newGraphics;
    }
    
    private static void increaseMaxX(int nx) {
        Image newImage = window.createImage(nx-minX, maxY-minY);
        Graphics2D newGraphics = (Graphics2D) newImage.getGraphics();
        
        newGraphics.setColor(Color.white);
        newGraphics.fillRect(0, 0, nx-minX, maxY-minY);
        newGraphics.drawImage(baseImage, 0, 0, null);
        maxX = nx;
        
        baseImage = newImage;
        baseGraphics = newGraphics;
    }
    
    private static void increaseMaxY(int ny) {
        Image newImage = window.createImage(maxX-minX, ny-minY);
        Graphics2D newGraphics = (Graphics2D) newImage.getGraphics();
        
        newGraphics.setColor(Color.white);
        newGraphics.fillRect(0, 0, maxX-minX, ny-minY);
        newGraphics.drawImage(baseImage, 0, 0, null);
        maxY = ny;
        
        baseImage = newImage;
        baseGraphics = newGraphics;
    }
    
	// -------------------------------------------------------------
	
	/**
	 * Sets the speed of the turtle in pixels/second.
	 *
	 * Speed is used for draw() and move() but not for turn().
	 *
	 * @param _speed The speed in pixels/second.
	 */
	public static void setSpeed(double _speed) {
        init();
        speed = _speed;
	}
	
	/**
	 * Sets the line color.
	 *
	 * @param _color The line color.
	 */
	public static void setColor(Color _color) {
        init();
        color = _color;
	}
	
	/**
	 * Sets the line thickness.
	 *
	 * @param thick The line thickness.
	 */
	public static void setThickness(int thick) {
        init();
        lineStroke = new BasicStroke(thick);
	}
	
	// -------------------------------------------------------------
	
	/**
	 * Clears the canvas, and resets params to their default.
	 *
	 * Resets speed, color, thickness. Centers the turtle and
	 * points it to the right.
	 */
	public static void clear() {
        init();
        
        x = imageWidth/2;
        y = imageHeight/2;
        
        endX = x;
        endY = y;
        
        theta = 0.0;
        speed = 100.0;
        
        color = Color.black;
        lineStroke = new BasicStroke(1);
		
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
	 * @param distance The distance in pixels.
	 * @see #setSpeed
	 * @see #setColor
	 * @see #setThickness
	 */
	public static void draw(double distance) {
        init();
        
		double totalDelay = distance / speed;
		moveSlowly(totalDelay, new LineUpdater(distance));
		
		synchronized (lock) {
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
	 * @param distance The distance in pixels.
	 * @see #setSpeed
	 */
	public static void move(double distance) {
        init();
        
		double totalDelay;
		double startX, startY;
		
        totalDelay = distance / speed;
        startX = x;
        startY = y;
		
		moveSlowly(totalDelay, new MoveUpdater(distance));
		
		synchronized (lock) {
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
	public static void jump(double _x, double _y) {
        init();
        
        endX = x = _x;
        endY = y = _y;
		
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
	public static void turn(double dtheta) {
        init();
        
        theta += dtheta / 180.0 * Math.PI;
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
	public static void turnRad(double dtheta) {
        init();
		turn(dtheta * 180.0 / Math.PI);
	}
	
    public static double getAngle() {
        return theta;
    }
    
    public static void setAngle(double _theta) {
        theta = _theta;
    }
    
    public static double getX() { return x; }
    public static double getY() { return y; }
    
    public static void setX(double _x) {
        x = _x;
        ensureAround((int)x, (int)y);
    }
    public static void setY(double _y) {
        y = _y;
        ensureAround((int)x, (int)y);
    }
    
    private static class Mark {
        double x, y, theta;
        Mark(double _x, double _y, double _theta) {
            this.x = _x;
            this.y = _y;
            this.theta = _theta;
        }
    }
    private static LinkedList<Mark> marks = new LinkedList<Mark>();
    
    public static void mark() {
        marks.push(new Mark(x, y, theta));
    }
    
    public static void reset() {
        if (marks.size() == 0) {
            throw new IllegalStateException("No marks");
        }
        Mark mark = marks.pop();
        x = mark.x;
        y = mark.y;
        theta = mark.theta;
        ensureAround((int)x, (int)y);
    }
    
	/**
	 * Pause for `millis` milliseconds.
	 *
	 * @param millis Milliseconds to pause.
	 */
	public static void pause(int millis) {
        init();
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			return;
		}
	}
	
	// -------------------------------------------------------------
	
	private static void fixLine() {
		draw(new LinePicture());
	}
	
	private static void clearBase() {
		draw(new ClearPicture());
	}
	
	private static void draw(final Picture pic) {
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
            Graphics2D g = baseGraphics;
            
			AffineTransform saveT = g.getTransform();
			g.translate(-minX, -minY);
			pic.draw(g);
			g.setTransform(saveT);
		}});
	}
	
	private static void moveSlowly(double time, Updater updater) {
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
	
	private static void drawActiveLine() {
		synchronized (lock) {
            Graphics2D g = activeGraphics;
            
			AffineTransform saveT = g.getTransform();
			g.translate(-shiftX, -shiftY);
			new LinePicture().draw(g);
			g.setTransform(saveT);
		}
	}
	
	private static void drawTurtle() {
		synchronized (lock) {
			Graphics2D g = activeGraphics;
			
			g.setColor(color);
			g.setStroke(turtleStroke);
			
			AffineTransform saveT = g.getTransform();
			
			g.translate(endX-shiftX, endY-shiftY);
			g.rotate(theta);
			
			g.drawLine( -5,  0, 5, 0 );
			g.drawLine(  0,  5, 5, 0 );
			g.drawLine(  0, -5, 5, 0 );
			
			g.setTransform(saveT);
		}
	}
	
	private static void updateScreenWrapper() {
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			updateScreen();
		}});
	}
	
	private static void updateScreen() {
		makeImage();
		if (activeGraphics == null || baseGraphics == null) {
			return;
		}
		
		synchronized (lock) {
            activeGraphics.setColor(Color.white);
            activeGraphics.fillRect(0, 0, imageWidth, imageHeight);
			activeGraphics.drawImage(baseImage, minX-shiftX, minY-shiftY, null);
			
			drawActiveLine();
			drawTurtle();
		}
		
		imageLabel.repaint();
	}
	
	private static void makeImage() {
		if (activeGraphics != null && baseGraphics != null) {
			return;
		}
		
		activeImage = window.createImage(imageWidth, imageHeight);
		baseImage   = window.createImage(imageWidth, imageHeight);
		
		if (activeImage != null)
			activeGraphics = (Graphics2D) activeImage.getGraphics();
		
		if (baseImage != null)
			baseGraphics = (Graphics2D) baseImage.getGraphics();
		
		if (activeImage != null) {
			imageLabel.setIcon(new ImageIcon(activeImage));
		}
	}
	
	// -----------------------------------------------------------------
	
	static interface Picture {
		public void draw(Graphics2D g);
	}
	
	static class LinePicture implements Picture {
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
			
			color  = Turtle.color;
			stroke = Turtle.lineStroke;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(color);
			g.setStroke(stroke);
			
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	static class ClearPicture implements Picture {
		Color color;
		
		ClearPicture() {
			color = Turtle.backgroundColor;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(color);
			g.fillRect(0, 0, imageWidth, imageHeight);
		}
	}
	
	static interface Updater {
		public void update(double progress);
	}
	
	static class LineUpdater implements Updater {
		double distance;
		
		LineUpdater(double _distance) {
			this.distance = _distance;
		}
		
		public void update(double progress) {
			double partDistance = progress * distance;
			
			synchronized (lock) {
				endX = x + partDistance * Math.cos(theta);
				endY = y + partDistance * Math.sin(theta);
                
                ensureAround((int)endX, (int)endY);
			}
			
			updateScreenWrapper();
		}
	}
	
	static class MoveUpdater implements Updater {
		double distance;
		double startX;
		double startY;
		
		MoveUpdater(double _distance) {
			this.distance = _distance;
			
			synchronized (lock) {
				startX = x;
				startY = y;
			}
		}
		
		public void update(double progress) {
			double partDistance = progress * distance;
			
			synchronized (lock) {
				endX = startX + partDistance * Math.cos(theta);
				endY = startY + partDistance * Math.sin(theta);
				
				x = endX;
				y = endY;
                
                ensureAround((int)endX, (int)endY);
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
		
		public void paintIcon(Component comp, Graphics g, int w, int h) { }
		
	    public int getIconWidth() { return width; }
	    public int getIconHeight() { return height; }
	}
}

