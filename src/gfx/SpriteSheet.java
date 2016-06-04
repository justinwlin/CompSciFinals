package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public String path;
	public int width;
	public int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));//IDK exactly what this does
			// Read more on documentation, but it basically just reads the image from the file. 
			// SO u pass it a path, or where to go, reference game class for what gets passed
			// and then it goes to that file and reads it. 
			// then we take that image and turn it into an rgb
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(image == null){
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		//Steal this online. Just limits the colors basically to four colors so easier to use. 
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = (pixels[i]&0xff)/64; //Gets rid of alpha channel. Divides by 64 bc of 255 / 64. 255 is the amount of colors.
		} //Creating a range of just 4 colors when u divide by 64.
		// get rid of 0xff and sees what happens... lol
		
		//debug purposes
		/*for(int i = 0; i < 8; i++){
			System.out.println(pixels[i]);
		}*/
		
	}
}
