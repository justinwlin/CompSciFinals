package gfx;

public class Screen {

	// Bits storing if it is mirrored or not. if mirrored
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	/*
	 * public static final int mirrorX = 1; public static final int mirrorY = 1;
	 */

	public int[] pixels;// stores the pixel array

	// ofsets
	public int xOffset = 0;
	public int yOffset = 0;

	// width and height of the screen
	public int width;
	public int height;

	public SpriteSheet sheet;// grabs the spritesheet for stuff

	// sets up the screen
	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];// array set to how big the screen is

	}

	// renders coor of the players, the tiles, the color, what direction the
	// player facing, add the scale
	/*
	 * did not create a character left and right, so instead just flip the
	 * character if a certain way by rendering it backwards
	 * 
	 * 
	 */

	// Sets offset....

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		int scaleMap = scale - 1;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
		for (int y = 0; y < 8; y++) {
			int ySheet = y;
			if (mirrorY) {// if passed to flip then flip
				ySheet = 7 - y;
			}
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
			// same here as y
			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				if (mirrorX) {
					xSheet = 7 - x;
				}
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				// col = the color at what pixel. &255 just makes it 0-255
				int col = (color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if (col < 255) {
					// prevents map from breaking. get rid of continue and see
					// what happens. u get arrayout of bounds
					// bc if you are moving about etc, and you at any time see
					// the "black" or the void tile, you are outside the array
					// as a result u must, when u are doing it, say that if you
					// move out then u have to continue.
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= height)// Basically
																				// calculating
																				// the
																				// yPixel
																				// +
																				// everything
																				// else
																				// that
																				// is
																				// going
																				// on.
							continue;// So player coordinate + map coordinate.
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width)
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}
	}

}
