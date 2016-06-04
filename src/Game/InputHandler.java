package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

	public InputHandler(Game game) {
		game.requestFocus(); // Request focus
		game.addKeyListener(this);// Game adds "this" class
	}

	public class Key {
		private boolean pressed = false;// Checks if a key is pressed
		public boolean isPressed() {
			return pressed;// Returns if pressed or not
		}
		public void toggle(boolean isPressed) {
			pressed = isPressed;
		}
	}
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	// Just key listener stuff
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}
	public void keyTyped(KeyEvent e) {}
	//Just sets up what is equal to the what key
	public void toggleKey(int KeyCode, boolean isPressed) {
		if (KeyCode == KeyEvent.VK_W || KeyCode == KeyEvent.VK_UP) {
			up.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_S || KeyCode == KeyEvent.VK_DOWN) {
			down.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_A || KeyCode == KeyEvent.VK_LEFT) {
			left.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_D || KeyCode == KeyEvent.VK_RIGHT) {
			right.toggle(isPressed);
		}
	}
}