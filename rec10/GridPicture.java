
import javax.swing.*;
import java.awt.*;

public class GridPicture {
	
	static Image image;
	static JLabel imageLabel;
	
	static Graphics2D imageGraphics;
	
	static JFrame window;
	
	static int width   = 500;
	static int height  = 500;
	
	static boolean setupDone = false;
	
	public static void setSize(int w, int h) {
		width  = w;
		height = h;
		
		image = null;
		setup();
	}
	
	private static void setup() {
		if (setupDone) {
			attemptImage();
			return;
		}
		
		setupDone = true;
		
		window = new JFrame("GridPicture");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageLabel = new JLabel(new DummyIcon(width, height));
		
		window.getContentPane().add(imageLabel);
		window.setVisible(true);
		
		attemptImage();
	}
	
	private static void attemptImage() {
		
		if (image != null)
			return;
		
		image = window.createImage(width, height);
		
		if (image == null) {
			imageLabel.setIcon(new DummyIcon(width, height));
		}
		else {
			imageLabel.setIcon(new ImageIcon(image));
			imageGraphics = (Graphics2D) image.getGraphics();
		}
		
		window.pack();
	}
	
	public static void color(int x, int y, Color color) {
		setup();
		
		if (imageGraphics == null)
			return;
		
		imageGraphics.setColor(color);
		imageGraphics.fillRect(x, y, 1, 1);
        imageLabel.repaint();
	}
	
	public static void color(int x, int y, int color) {
		color(x, y, new Color(color));
	}
	
	public static void color(int x, int y, int r, int g, int b) {
		r = Math.min(Math.max(r, 0), 255);
		g = Math.min(Math.max(g, 0), 255);
		b = Math.min(Math.max(b, 0), 255);
		
		color(x, y, new Color(r, g, b));
	}
	
	// -------------------------------------------------------------------
	
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
