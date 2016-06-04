package Entity;

import Game.InputHandler;
import gfx.Colors;
import gfx.Screen;
import level.Level;

public class Player2 extends Mob {

	private InputHandler input;
	// private int colour = Colors.get(-1, 111, 145, 543);
	private int colour = Colors.get(-1, 111, 145, 543);//PLayer color. Play around and see. 
	// RGB value, each number is 0-5
	// Reference sprite sheet btw
	/*
	 * Srite sheet is made up of background, and 3 colors
	 * background is set to transparent
	 * 
	 * other things is by RGB values
	 * 
	 * so color one is = r, g, b
	 * color 2 = r,g,b etc.
	 */
	private int scale = 1;

	public Player2(Level level, int x, int y, InputHandler input) {
		super(level, "Player2", x, y, 1);
		this.input = input;
	}
	
	//Checks for keyboard
	public void tick() {
		int xpos = 0;
		int ypos = 0;

		if (input.up.isPressed()) {
			ypos -= 1;
		}
		if (input.down.isPressed()) {
			ypos += 1;
		}
		if (input.left.isPressed()) {
			xpos -= 1;
		}
		if (input.right.isPressed()) {
			xpos += 1;
		}

		if (xpos != 0 || ypos != 0) {
			move(xpos, ypos);
			isMoving = true;
		} else {
			isMoving = false;
		}
		// this.scale = 1;
		// Change if u want to see what scale does.
	}
	
	/*
	 * Check the spritesheet
	 * 
	 * character is at 0
	 * y = 28
	 * 
	 * MOst of the stuff taken from something online bc i didn't know how to move 4 character boxes at once
	 * 
	 * but is relatively easy to know
	 * 
	 * only thing i can't really explain is the flip top and bottom, i just know if u screw with it, u really mess up the math on where the boxes are rendered
	 * 
	 */
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 30;
		int walkingSpeed = 4;

		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;

		if (movingDir == 1) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;

		}

		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;

		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale); // upper															// body part
																											// 1
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop,
				scale); // upper body part 2
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour,
				flipBottom, scale); // lower body part 1
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,
				colour, flipBottom, scale); // lower
		// body
		// part
		// 2
	}
	
	//sets up the collision box
	public boolean hasCollided(int xa, int ya) {
        int xMin = 0;//This is based on the character sprite... soo like near the legs is the collision box
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }


}