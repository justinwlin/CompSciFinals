package level;

public class solidTile extends BasicTile {

	public solidTile(int id, int x, int y, int tileColor, int levelColor){
		super(id, x, y, tileColor, levelColor);
		this.solid = true;//Refer to Tile class
	}
}
