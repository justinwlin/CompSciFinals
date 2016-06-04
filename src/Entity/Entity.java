package Entity;

import gfx.Screen;
import level.Level;

public abstract class Entity {
	public int x, y;
	protected Level level;
	
	public Entity(Level level){
		init(level);
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
	
	/*
	 * x,y = coordinates
	 * level = tells what level
	 * init level = just creates the level that the entity goes to 
	 * tick is the update, basically later on telling stuff to draw
	 * render tells where the screen goes.
	 */
}
