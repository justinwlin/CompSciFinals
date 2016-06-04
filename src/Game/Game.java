package Game;

import gfx.Screen;
import gfx.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Entity.Player;
import Entity.Player2;
import level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;// Ignore

	private JFrame frame;// Starting up Jframe

	public static final int WIDTH = 160;// Width
	public static final int HEIGHT = WIDTH / 12 * 9; // Height
	public static final int SCALE = 3; // Scale
	public static final String NAME = "Game"; // Name
	private Screen screen; // Grabs the screen
	public InputHandler input;// Looks at the keyboard
	public boolean running = false;// Is the game running
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);// Turns
																								// screen
																								// into
																								// an
																								// image
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();// Gets
																							// the
																							// pixels
																							// of
																							// that
																							// image
	private int[] colors = new int[6 * 6 * 6 * 6];// Buggy. I got a lot of
													// nullpointers so i just
													// made the colors bigger.
													// but is meant to story the
													// colors of the pixels

	public Level level;// Downloads level
	public Player2 player;// Player
	public String load = "/Levels/test_level2.png";

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));// Sets the
																		// sizes
																		// of
																		// the
																		// game
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);// Sets the title of the game

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Close game when
																// u hit x
		frame.setLayout(new BorderLayout());// Set layout
		frame.add(this, BorderLayout.CENTER);// Center the frame
		frame.pack();// Makes it go to the size of the frame

		frame.setResizable(false);// No resizing
		frame.setLocationRelativeTo(null);// Centers
		frame.setVisible(true);// Makes visible
	}

	public synchronized void start() {// starts the game
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {// ends the game
		running = false;
	}

	public void run() {// Clock. Won't get into too much detail. Just copy and
						// past off web. Mostly just to keep the game running at
						// a certain speed.
		long time1 = System.nanoTime();
		long time2 = System.currentTimeMillis();
		double nsPerTick = 1000000000D / 60D;
		double delta = 0;
		int ticks = 0;
		int frames = 0;

		init();
		requestFocus();// Keeps the game focused so u don't have to click to
						// start it.
		while (running) {
			long now = System.nanoTime();
			delta += (now - time1) / nsPerTick;
			time1 = now;
			boolean render = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				render = true;
			}
			if (render) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - time2 >= 1000) {
				time2 += 1000;
				frame.setTitle(NAME + "  | " + ticks + " ups, " + frames + " fps");// Modification.
																					// Just
																					// so
																					// that
																					// it
																					// tells
																					// everything
																					// going
																					// on
																					// in
																					// the
																					// name
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick() {// Tick method... yeah.. i mean... it just updates the
						// level
		level.tick();}

	public void render() {// Draw stuff
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);// Buffer image of three. Basically frames
									// in storage waiting to go next.
			return;
		}

		int xOffset = player.x - (screen.width / 2);// Centers the player. Math
													// man.
		int yOffset = player.y - (screen.height / 2);

		level.renderTiles(screen, xOffset, yOffset);
		/*
		 * Renders the tiles. you have offsets bc if the player is in the center
		 * of the screen, and it moves right one then you need to render the
		 * pixels to the right by 1, not by a full tile.
		 * 
		 * so you need to pass parameters by how much you "offset" the screen
		 * by, so it knows how much of the tile to actually render
		 */
		level.renderEntities(screen);// Adds entities to the screen.

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int ColorCode = screen.pixels[x + y * screen.width];
				if (ColorCode < 255) {
					pixels[x + y * WIDTH] = colors[ColorCode];
					/*
					 * Goes through entire screen ColorCode is the RGB values of
					 * that pixel. If the color code is less than 225, than set
					 * that color(bc colors are done in RGB style) To the array
					 * colors, so that we can reference that array later on.
					 * 
					 * It is less than 255, even though in total there is 255,
					 * is bc 255 will be transparent color
					 * 
					 * 
					 */
				}
			}
		}
		// Draws graphic
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void init() {// initialize stuff
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colors[index++] = rr << 16 | gg << 8 | bb;// Stores the RGB
																// value in a
																// byte number
					// Learn how bytes work if you don't know. Basically
					/*
					 * First of all the shades of RGB is divided into 6 just to
					 * keep it simple. so 6 shade of red, 6 shade of blue, 6
					 * shade of green
					 *
					 * In order to store the values you store it into a number
					 * Each tile is a 8x8 pixels so u need to shift it by a
					 * multiple of 8 each time.
					 * 
					 * rr << 16, g<<8, b | is just to combine the numbers in
					 * terms of bit-wise operation
					 * 
					 * so if your number is 1000 and 0001 and you do 1000 | 0001
					 * you get 1001
					 *
					 */

				}
			}
		}

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));// Just
																					// sending
																					// the
																					// screen
																					// the
																					// spritesheet
																					// that
																					// we
																					// are
																					// using
		input = new InputHandler(this); // Inserts the keyboard

		level = new Level(load);// Sets the level.

		/*
		 * To do: Create a level change class.
		 */
		player = new Player2(level, 32, 32, input);// Creates the player and
													// passes it the input
													// keyboard. Spawns at 1,1
													// so not in the wall
		level.addEntity(player); // Level adds the player to the level. Adds it
									// to an arraylist in entity in order to
									// keep track of all the entities.
	}

	// Starts the game...
	public static void main(String[] args) {
		new Game().start();
	}

}