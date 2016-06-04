package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Entity;
import gfx.Screen;

public class Level {

	private byte[] tiles;
	public int width;
	public int height;
	public ArrayList<Entity> entities = new ArrayList<Entity>();

	private String path;
	private BufferedImage image;

	public Level(String path) {
		if (path != null) {
			this.path = path;
			this.loadLevelFromFile();
		} else {
			tiles = new byte[width * height];
			this.width = 64;
			this.height = 64;
			this.generateLevel();
		}
	}

	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 10 < 7) {
					tiles[x + y * width] = Tile.GRASS.getId();
				} else {
					tiles[x + y * width] = Tile.STONE.getId();
				}
			}
		}
	}

	private void loadTiles() {
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
						this.tiles[x + y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	public void alterTile(int x, int y, Tile nT) {
		this.tiles[x + y * width] = nT.getId();
		image.setRGB(x, y, nT.getLevelColor());
	}


	public void renderTiles(Screen screen, int xOff, int yOff) {
		if (xOff < 0)
			xOff = 0;
		if (xOff > ((width << 3) - screen.width))
			xOff = ((width << 3) - screen.width);
		if (yOff < 0)
			yOff = 0;
		if (yOff > ((height << 3) - screen.height))
			yOff = ((height << 3) - screen.height);
		screen.setOffset(xOff, yOff);
		for (int y = (yOff >> 3); y < (yOff + screen.height >> 3) + 1; y++) {
			for (int x = (xOff >> 3); x < (xOff + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}
	
	public void tick() {
		for (Entity entity : entities) {
			entity.tick();
		}
	}
	
	public void renderEntities(Screen screen) {
		for (Entity entity : entities) {
			entity.render(screen);
		}
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
}