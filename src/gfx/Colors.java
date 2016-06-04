package gfx;

public class Colors {
//Color is: 255, 170, 85, 0
	//Should be pretty self explanator
	/// basically u pass it 4 colors, and it stores it into a byte 
	// look up byte calculators if u don't get it
        public static int get(int c1, int c2, int c3, int c4) {
                return ((get(c4) << 24) + (get(c3) << 16)
                                + (get(c2) << 8) + (get(c1)));
        }
        
        //parses that byte number so that u can get how much of each rgb
        private static int get(int c) {
                if (c < 0) {
                }
                int r = c / 100 % 10;
                int g = c/ 10 % 10;
                int b = c % 10;
                return (r * 36) + (g * 6) + b;
        }
}

