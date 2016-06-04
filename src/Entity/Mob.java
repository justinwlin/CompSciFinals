package Entity;

import level.Level;
import level.Tile;
/*name = name of the mob
* speed is how fast it is moving... if you want to play around with this
* change the speed and sees what happens. does what u expect, lol.
* 
* numsteps is a bit complicated to explain, but will be used in the player class
* mostly to help control speed
* 
* boolean ismoving tells if it is moving
* 
* dir is just telling what direction
* 
* 0 = north
* 1 = south
* 2 = east
* 3 = west
* 
* scale is just what scale it is. In case we want a big character etc. 
* 
* just change it up and you'll see what happens, makes the character bigger or smaller

* */

public abstract class Mob extends Entity {

	protected int numSteps = 0;
	protected boolean isMoving;

	protected String name;
	protected int speed;

	protected int movingDir = 1;
	protected int scale = 1;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;

		// Direction at when u are moving
		if (!hasCollided(xa, ya)) {
			if (ya < 0)
				movingDir = 0;
			if (ya > 0)
				movingDir = 1;
			if (xa < 0)
				movingDir = 2;
			if (xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null) {
			return false;
		}
		// checks last tile, then checks the one that u are going to even more.
		// hence x + x <-- imagine u are against a wall, you try to input
		// command
		// if you try to move more, xa, and is the same tile
		// and the tile is not solid
		// return true to move
		// else false.
		Tile nT = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		Tile lT = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		if (!lT.equals(nT) && nT.isSolid()) {
			return true;
		}
		return false;
	}

	/*
	 * lazy to explain. Just ignore it though. Just get methods and stuff.
	 */
	public String getName() {
		return name;
	}

	public int getDir() {
		return movingDir;
	}

	public void setNS(int numSteps) {
		this.numSteps = numSteps;
	}

	public int getNS() {
		return numSteps;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void setDir(int movingDir) {
		this.movingDir = movingDir;
	}
}