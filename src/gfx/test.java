package gfx;
/*
 * Debugging with binary numbers, just ignore
 * 
 * 
 * 
 * 
 */
public class test {
	public static void main(String args[]){
		System.out.println(16 & 1);
		System.out.println(16 & 2);
		System.out.println(16 & 3);
		System.out.println(16 & 4);
		System.out.println(16 & 5);
		System.out.println(10000|0001);
		
		int rr = 01;
		int bb = 01;
		int gg = 01;
		
		rr = rr << 8;
		gg = gg << 4;
		
		System.out.println(rr | gg| bb);//Returns 273. Int form. Convert to binary and will see that is equal.
	}
}
