package level;

import gfx.Screen;

public class BasicTile extends Tile {

	protected int tileId;
	protected int tileColor;
	
	//Tilecolor is the actual tile color that gets rendered
	// level color is what color the tile is in the level editor. Basically what solid color it is when you upload an image.
	
	public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, levelColor);
		this.tileId = x + y;
		this.tileColor = tileColor;
	}
	
	public void render(Screen screen, Level level, int x, int y) {screen.render(x, y, tileId, tileColor, 0x00, 1);
	}

}