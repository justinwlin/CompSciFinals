package level;

import gfx.Colors;
import gfx.Screen;

public abstract class Tile {

	/*
	 * NOTE!!! MAKE SURE TO CHANGE THE ID FOR EACH TILE!!! IF YOU DON"T THE IDS
	 * WILL GET CONFUSED
	 * 
	 * 
	 */
	public static final Tile[] tiles = new Tile[256];
	//(ID, x, y (note, the x and y is referencing to the tile sheet), color, and the level load color)
	public static final Tile VOID = new solidTile(0, 0, 0, Colors.get(000, -1, -1, -1), 0xFF00000);
	// 0xFF00000 = black
	public static final Tile STONE = new solidTile(1, 1, 0, Colors.get(-1, 321, 0, 0), 0xFF555555);
	// Colors are done by RGB again
	// Gray
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1), 0xFF00FF00);
	// green

	protected byte id;
	protected boolean solid;
	private int levelColor;

	public Tile(int id, boolean isSolid, int color) {
		this.id = (byte) id;
		this.solid = isSolid;
		this.levelColor = color;
		tiles[id] = this;
	}
	public byte getId() {return id;}
	public boolean isSolid() {return solid;}
	public int getLevelColor() {return levelColor;}
	public abstract void render(Screen screen, Level level, int x, int y);

}